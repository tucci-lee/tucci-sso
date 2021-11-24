package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.model.body.AvatarEditBody;
import cn.tucci.sso.server.model.body.UserProfileEditBody;
import cn.tucci.sso.server.model.domain.UserProfile;
import cn.tucci.sso.server.model.dto.AvatarEditDTO;
import cn.tucci.sso.server.model.dto.UserProfileDTO;
import cn.tucci.sso.server.service.UserProfileService;
import cn.tucci.sso.server.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping(value = {"profile", "app/profile"})
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * 获取用户信息
     *
     * @return UserProfileDTO
     */
    @GetMapping
    public Result<UserProfileDTO> profile() {
        Long uid = Subject.get();
        UserProfileDTO profile = userProfileService.getByUid(uid);
        return Result.ok(profile);
    }

    /**
     * 修改头像
     *
     * @param body 修改信息
     * @return 新头像地址
     * @throws IOException 获取流失败
     */
    @PutMapping("avatar/edit")
    public Result<?> editAvatar(@Validated AvatarEditBody body) throws IOException {
        Long uid = Subject.get();
        String avatar = userProfileService.editAvatar(uid, body.getFile());
        AvatarEditDTO result = new AvatarEditDTO()
                .setAvatar(avatar);
        return Result.ok(result);
    }

    /**
     * 修改用户信息
     *
     * @param body 修改的信息
     * @return ok
     */
    @PutMapping("edit")
    public Result<?> edit(@Validated @RequestBody UserProfileEditBody body) {
        Long uid = Subject.get();
        UserProfile edit = new UserProfile()
                .setUid(uid);
        BeanUtils.copyProperties(body, edit);
        userProfileService.editById(edit);
        return Result.ok();
    }
}
