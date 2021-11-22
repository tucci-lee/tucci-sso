package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import cn.tucci.sso.server.core.constant.VerifyConst;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class UserProfileEditBody {

    @NotBlank(message = I18nConst.NICKNAME_NOT_NULL)
    @Size(max = 20, message = I18nConst.NICKNAME_FORMAT_ERROR)
    private String nickname;

    private String birthday;

    @NotBlank(message = I18nConst.REGION_NOT_NULL)
    private String province;

    @NotBlank(message = I18nConst.REGION_NOT_NULL)
    public String city;

    @NotNull(message = I18nConst.GENDER_NOT_NULL)
    @Max(value = VerifyConst.Gender.MAX, message = I18nConst.PARAMETER_ERROR)
    private Integer gender;

    private String introduction;

}
