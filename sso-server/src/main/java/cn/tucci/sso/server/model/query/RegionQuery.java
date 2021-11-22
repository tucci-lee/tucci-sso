package cn.tucci.sso.server.model.query;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tucci.lee
 */
@Data
public class RegionQuery {

    @NotBlank(message = I18nConst.PARAMETER_ERROR)
    private String parentId;
}
