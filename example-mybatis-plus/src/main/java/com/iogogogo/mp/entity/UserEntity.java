package com.iogogogo.mp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.Data;

/**
 * Created by tao.zeng on 2020-01-11.
 */
@Data
public class UserEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long uid;

    public UserEntity() {
        IdWorker.getId();
    }
}
