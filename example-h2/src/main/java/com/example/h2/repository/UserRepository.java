package com.example.h2.repository;

import com.example.h2.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tao.zeng on 2020-01-05.
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
