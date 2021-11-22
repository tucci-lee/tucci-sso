package cn.tucci.sso.server.service;

import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.task.AsyncTaskExecutor;
import cn.tucci.sso.server.core.utils.Assert;
import cn.tucci.sso.server.model.dao.UserProfileMapper;
import cn.tucci.sso.server.model.domain.UserProfile;
import cn.tucci.sso.server.model.dto.UserProfileDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tucci.lee
 */
@Service
public class UserProfileService extends ServiceImpl<UserProfileMapper, UserProfile> {

    private final OssService ossService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public UserProfileService(OssService ossService,
                              AsyncTaskExecutor asyncTaskExecutor) {
        this.ossService = ossService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 添加一条用户基础信息
     *
     * @param uid uid
     */
    public void add(Long uid) {
        UserProfile profile = new UserProfile()
                .setUid(uid);
        baseMapper.insert(profile);
    }

    /**
     * 获取用户基础信息
     *
     * @param uid uid
     * @return 用户基础信息
     */
    public UserProfileDTO getByUid(Long uid) {
        return baseMapper.selectByUid(uid);
    }

    /**
     * 修改用户头像
     * 1. 上传头像并获取对象存储中的全路径
     * 2. 获取旧头像地址
     * 3. 更新头像
     * 4. 异步执行删除旧头像
     *
     * @param uid  uid
     * @param file 头像
     * @return 头像路径
     */
    public String editAvatar(Long uid, MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        Assert.isTrue(contentType != null && contentType.contains("image"), ResultStatus.FILE_TYPE_ERROR);
        String key = ossService.getKey(OssService.AVATAR, file.getOriginalFilename());
        ossService.putObject(key, file.getInputStream());
        String newAvatar = ossService.getObjectUrl(key);

        UserProfile profile = baseMapper.selectById(uid);
        String oldAvatar = profile.getAvatar();

        UserProfile edit = new UserProfile()
                .setUid(uid)
                .setAvatar(newAvatar);
        baseMapper.updateById(edit);

        if (oldAvatar != null) {
            asyncTaskExecutor.execute(() -> {
                String oldOssKey = oldAvatar.replace(ossService.getHost() + "/", "");
                ossService.deleteObject(oldOssKey);
            });
        }
        return newAvatar;
    }
}
