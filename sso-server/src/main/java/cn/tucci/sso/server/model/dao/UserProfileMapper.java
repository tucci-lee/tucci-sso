package cn.tucci.sso.server.model.dao;


import cn.tucci.sso.server.model.domain.UserProfile;
import cn.tucci.sso.server.model.dto.UserProfileDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserProfileMapper extends BaseMapper<UserProfile> {

    UserProfileDTO selectByUid(Long uid);
}