package cn.tucci.sso.server.model.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("user_profile")
public class UserProfile implements Serializable {
    @TableId(type = IdType.NONE)
    private Long uid;

    private String avatar;

    private String nickname;

    private String birthday;

    private String province;

    private String city;

    private Integer gender;

    private String introduction;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    private static final long serialVersionUID = 1L;
}