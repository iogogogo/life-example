package com.iogogogo.vertica;

import com.iogogogo.vertica.entity.UserEntity;
import com.iogogogo.vertica.mapper.UserMapper;
import com.iogogogo.vertica.persistent.SqlSessionFactoryHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tao.zeng on 2019-03-12.
 */
@Slf4j
public class VerticaApplication {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:vertica://192.168.21.188:5433/vertica20190122001", "dbadmin", "123456");

        log.info("connection:{}", connection);

        SqlSession session = SqlSessionFactoryHelper.openSqlSession();
        log.info("session:{}", session);

        UserMapper userMapper = session.getMapper(UserMapper.class);
        boolean b = userMapper.save(new UserEntity(2052L, "哈哈哈哈", "灌灌灌灌或或或或"));
        log.info("b:{}", b);
        session.commit();

        userMapper.findAll().forEach(x -> log.info(x.toString()));


    }
}
