package cn.tucci.sso.server.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class AvatarEditDTO {

    private String avatar;
}
