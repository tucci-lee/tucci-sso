package cn.tucci.sso.server.model.body;

import cn.tucci.sso.server.core.constant.I18nConst;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class AvatarEditBody {

    @NotNull(message = I18nConst.AVATAR_NOT_NULL)
    private MultipartFile file;
}
