package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import cn.tucci.sso.server.core.constant.VerifyConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class PasswordEditBody {
    @NotBlank(message = I18nConst.PASSWORD_NOT_NULL)
    @Pattern(regexp = VerifyConst.PASSWORD_REGEXP, message = I18nConst.PASSWORD_FORMAT_ERROR)
    private String password;

    @NotBlank(message = I18nConst.PARAMETER_ERROR)
    private String token;
}
