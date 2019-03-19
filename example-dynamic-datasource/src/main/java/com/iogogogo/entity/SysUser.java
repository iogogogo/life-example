package com.iogogogo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tao.zeng on 2019-03-19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model<SysUser> implements Serializable {

    private long id;

    private String name;

    private LocalDateTime birthday;
}
