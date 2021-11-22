package cn.tucci.sso.server.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserProfileDTO {
    private Long uid;

    private String username;

    private String avatar;

    private String nickname;

    private String birthday;

    private String province;

    private String city;

    private Integer gender;

    private String introduction;

    private Long createTime;
}
