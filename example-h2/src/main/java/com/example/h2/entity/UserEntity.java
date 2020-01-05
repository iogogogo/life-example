package com.example.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tao.zeng on 2020-01-05.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("user_info")
public class UserEntity implements Serializable {

    @Id
    private Long id;

    private String name;

    @Column("birthday")
    private LocalDateTime birthday;

    private String remark;


    public UserEntity(String name, LocalDateTime birthday, String remark) {
        this.name = name;
        this.birthday = birthday;
        this.remark = remark;
    }
}
