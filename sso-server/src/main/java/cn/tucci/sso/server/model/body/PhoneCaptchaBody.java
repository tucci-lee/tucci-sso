package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import cn.tucci.sso.server.core.constant.VerifyConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class PhoneCaptchaBody {

    @NotBlank(message = I18nConst.PHONE_NOT_NULL)
    @Pattern(regexp = VerifyConst.PHONE_REGEXP, message = I18nConst.PHONE_FORMAT_ERROR)
    private String phone;

    @NotNull(message = I18nConst.PARAMETER_ERROR)
    private Integer type;
}
