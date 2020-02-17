package com.example.h2.repository;

import com.example.h2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tao.zeng on 2020-01-05.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
