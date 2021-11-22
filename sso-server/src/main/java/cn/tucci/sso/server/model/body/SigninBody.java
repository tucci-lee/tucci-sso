package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class SigninBody {

    @NotBlank(message = I18nConst.USERNAME_NOT_NULL)
    private String username;

    @NotBlank(message = I18nConst.PASSWORD_NOT_NULL)
    private String password;

    @NotBlank(message = I18nConst.CAPTCHA_NOT_NULL)
    private String captcha;

    @NotNull(message = I18nConst.PARAMETER_ERROR)
    private Boolean rememberMe;

    private String redirectUri;
}
