package com.iogogogo.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
@Builder
@EqualsAndHashCode(callSuper = true)
public class Role extends Model<Role> {

    @TableId(type = IdType.AUTO)
    private long id;

    private String name;

}
