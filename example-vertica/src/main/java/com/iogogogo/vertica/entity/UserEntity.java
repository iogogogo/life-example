package com.iogogogo.vertica.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by tao.zeng on 2019-03-13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    private Long id;

    private String name;

    private String description;

    public UserEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
