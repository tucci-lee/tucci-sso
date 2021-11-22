package cn.tucci.sso.server.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
@TableName("region")
public class Region implements Serializable {
    @TableId(type = IdType.NONE)
    private String regionId;

    private String parentId;

    private String name;

    private String enName;

    private static final long serialVersionUID = 1L;
}
