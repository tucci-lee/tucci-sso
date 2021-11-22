package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class AccountPhoneCaptchaBody {

    @NotNull(message = I18nConst.PARAMETER_ERROR)
    private Integer type;
}
