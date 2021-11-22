package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author tucci.lee
 */
@Data
public class EmailEditBody {
    @NotBlank(message = I18nConst.PARAMETER_ERROR)
    private String token;

    @NotBlank(message = I18nConst.EMAIL_NOT_NULL)
    @Email(message = I18nConst.EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = I18nConst.CAPTCHA_NOT_NULL)
    private String captcha;
}
