package com.iogogogo;

import com.iogogogo.entity.SysUser;
import com.iogogogo.entity.User;
import com.iogogogo.mapper.mysql.SysUserMapper;
import com.iogogogo.mapper.vertica.UserMapper;
import com.iogogogo.util.IdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tao.zeng on 2019-03-15.
 */
@Slf4j
@SpringBootApplication
public class DynamicDataSourceApplication implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        List<User> list = userMapper.list();
        log.info("list.size() {}", list.size());
        boolean b = sysUserMapper.save(new SysUser(IdHelper.id(), "小花脸-" + IdHelper.uuid(), LocalDateTime.now()));
        log.info("insert:{}", b);
        List<SysUser> users = sysUserMapper.list();
        users.forEach(x -> log.info(x.toString()));

        users =  sysUserMapper.selectList(null);
        users.forEach(x -> log.info(x.toString()));

    }
}
