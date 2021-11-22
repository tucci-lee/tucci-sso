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
public class PhoneEditBody {
    @NotBlank(message = I18nConst.PARAMETER_ERROR)
    private String token;

    @NotBlank(message = I18nConst.PHONE_NOT_NULL)
    @Pattern(regexp = VerifyConst.PHONE_REGEXP, message = I18nConst.PHONE_FORMAT_ERROR)
    private String phone;

    @NotBlank(message = I18nConst.CAPTCHA_NOT_NULL)
    private String captcha;
}
