package cn.tucci.sso.server.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserAccountDTO {

    private Long uid;

    private String email;

    private String phone;
}
