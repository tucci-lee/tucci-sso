package cn.tucci.sso.server.service;

import cn.tucci.sso.server.model.domain.UserProfile;
import cn.tucci.sso.server.model.dto.UserProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tucci.lee
 */
public interface UserProfileService {

    /**
     * 添加用户基础信息
     *
     * @param uid uid
     */
    void add(Long uid);

    /**
     * 查询用户基础信息
     *
     * @param uid uid
     * @return 基础信息
     */
    UserProfileDTO getByUid(Long uid);

    /**
     * 修改头像
     *
     * @param uid  uid
     * @param file 头像文件
     * @return 新头像地址
     */
    String editAvatar(Long uid, MultipartFile file) throws IOException;

    /**
     * 修改用户基础信息
     *
     * @param profile 基础信息
     */
    void editById(UserProfile profile);
}
