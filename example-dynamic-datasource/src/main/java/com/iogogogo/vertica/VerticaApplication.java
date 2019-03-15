package com.iogogogo.vertica;

import com.iogogogo.vertica.entity.User;
import com.iogogogo.vertica.mapper.UserMapper;
import com.iogogogo.vertica.service.UserService;
import com.iogogogo.vertica.util.IdHelper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tao.zeng on 2019-03-15.
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.iogogogo.vertica.mapper")
public class VerticaApplication implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(VerticaApplication.class, args);
    }

    @Override
    public void run(String... args) {

        List<User> list = new ArrayList<>(1024);
        for (int i = 0; i < 1023; i++) {
            list.add(new User(IdHelper.id(), "小花脸-" + IdHelper.uuid(), "哈哈哈哈哈-" + IdHelper.uuid()));
        }

        boolean b = userService.saveBatch(list);
        log.info("b:{}", b);


        List<User> users = userMapper.list();
        users.forEach(x -> log.info(x.toString()));
    }
}
