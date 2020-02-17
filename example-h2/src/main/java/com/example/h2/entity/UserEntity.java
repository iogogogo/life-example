package com.example.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tao.zeng on 2020-01-05.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserEntity implements Serializable {

    @Id
    private Long id;

    private String name;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    private String remark;


    public UserEntity(String name, LocalDateTime birthday, String remark) {
        this.name = name;
        this.birthday = birthday;
        this.remark = remark;
    }
}
