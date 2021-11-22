package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import cn.tucci.sso.server.core.constant.VerifyConst;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class PasswordVerifyBody {

    @NotNull(message = I18nConst.PARAMETER_ERROR)
    @Range(min = VerifyConst.SignupType.MIN, max = VerifyConst.SignupType.MAX, message = I18nConst.PARAMETER_ERROR)
    private Integer type;

    @Pattern(regexp = VerifyConst.PHONE_REGEXP, message = I18nConst.PHONE_FORMAT_ERROR)
    private String phone;

    @Email(message = I18nConst.EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = I18nConst.CAPTCHA_NOT_NULL)
    private String captcha;
}
