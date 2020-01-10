package com.iogogogo.redis.configure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by tao.zeng on 2020-01-10.
 */
@Slf4j
public class RedisOperations {

    private RedisTemplate redisTemplate;

    private RedisOperations() {
    }

    public RedisOperations(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate redisTemplate() {
        return redisTemplate;
    }
}
