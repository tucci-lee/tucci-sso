package cn.tucci.sso.server.service.impl;

import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.task.AsyncTaskExecutor;
import cn.tucci.sso.server.core.utils.Assert;
import cn.tucci.sso.server.model.dao.UserProfileMapper;
import cn.tucci.sso.server.model.domain.UserProfile;
import cn.tucci.sso.server.model.dto.UserProfileDTO;
import cn.tucci.sso.server.service.FsService;
import cn.tucci.sso.server.service.UserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tucci.lee
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileMapper userProfileMapper;
    private final FsService fsService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public UserProfileServiceImpl(UserProfileMapper userProfileMapper,
                                  FsService fsService,
                                  AsyncTaskExecutor asyncTaskExecutor) {
        this.userProfileMapper = userProfileMapper;
        this.fsService = fsService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void add(Long uid) {
        UserProfile profile = new UserProfile()
                .setUid(uid);
        userProfileMapper.insert(profile);
    }

    @Override
    public UserProfileDTO getByUid(Long uid) {
        UserProfileDTO result = userProfileMapper.selectByUid(uid);
        if(result.getAvatar() != null){
            String domain = fsService.getDomain();
            result.setAvatar(domain + result.getAvatar());
        }
        return result;
    }

    /**
     * 修改用户头像
     * 1. 上传头像
     * 2. 获取旧头像地址
     * 3. 更新头像
     * 4. 异步执行删除旧头像
     *
     * @param uid  uid
     * @param file 头像
     * @return 头像路径
     */
    @Override
    public String editAvatar(Long uid, MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        Assert.isTrue(contentType != null && contentType.contains("image"), ResultStatus.FILE_TYPE_ERROR);
        String key = fsService.getKey(FsService.AVATAR, file.getOriginalFilename());
        fsService.upload(key, file.getInputStream());

        UserProfile profile = userProfileMapper.selectById(uid);
        String oldAvatar = profile.getAvatar();

        UserProfile edit = new UserProfile()
                .setUid(uid)
                .setAvatar(key);
        userProfileMapper.updateById(edit);

        if (oldAvatar != null) {
            asyncTaskExecutor.execute(() -> fsService.delete(oldAvatar));
        }
        return fsService.getDomain() + key;
    }

    @Override
    public void editById(UserProfile profile) {
        userProfileMapper.updateById(profile);
    }
}
