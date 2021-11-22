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
@TableName("user_account")
public class UserAccount implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long uid;

    private String email;

    private String phone;

    private String username;

    private String password;

    private Boolean isLock;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}