package com.iogogogo.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class User extends Model<User> {

    @TableId(type = IdType.AUTO)
    private long id;

    private String username;

    private String password;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
