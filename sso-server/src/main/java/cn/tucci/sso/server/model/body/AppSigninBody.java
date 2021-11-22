package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tucci.lee
 */
@Data
public class AppSigninBody {

    @NotBlank(message = I18nConst.USERNAME_NOT_NULL)
    private String username;

    @NotBlank(message = I18nConst.PASSWORD_NOT_NULL)
    private String password;

}
