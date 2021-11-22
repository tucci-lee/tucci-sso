package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class PhoneEditVerifyBody {

    @NotNull(message = I18nConst.PARAMETER_ERROR)
    private Integer type;

    @NotBlank(message = I18nConst.CAPTCHA_NOT_NULL)
    private String captcha;
}
