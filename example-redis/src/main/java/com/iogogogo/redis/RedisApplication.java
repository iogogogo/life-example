package com.iogogogo.redis;

import com.iogogogo.redis.configure.handler.RedisHandler;
import com.iogogogo.redis.configure.util.RedisOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by tao.zeng on 2020-01-10.
 */
@Slf4j
@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate redisTemplateDB_2;

    @Autowired
    private RedisHandler redisHandler;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("redisTemplate:{}", redisTemplate);
        redisTemplate.opsForValue().set("iogogogo", "redisTemplate save value");


        log.info("redisTemplateDB_2:{}", redisTemplateDB_2);
        redisTemplateDB_2.opsForHash().put("iogogogo", "iogogogo-hash", "redisTemplateDB_2 save value");


        // 通过统一的RedisOperations对Redis进行操作
        // 这里可以自己封装一些常用的方法，这样就能把下层的RedisTemplate进行封装，对外仅仅只是一个RedisOperations而已
        // 当然这里也提供对应的方法获取不同RedisTemplate对象最后的封装实例
        RedisOperations redisOperations = redisHandler.redisOperations();
        RedisOperations redisDB2Operations = redisHandler.redisDB2Operations();

        log.info("redisOperations redisTemplate:{}", redisOperations.redisTemplate());
        log.info("redisDB2Operations redisTemplate:{}", redisDB2Operations.redisTemplate());
    }
}
