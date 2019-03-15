package com.iogogogo.vertica.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * Created by tao.zeng on 2019-03-15.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("public.user")
@EqualsAndHashCode(callSuper = true)
public class User extends Model<User> implements Serializable {

    private Long id;

    private String name;

    private String description;
}
