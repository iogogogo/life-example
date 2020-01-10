package com.iogogogo.redis.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple;
import io.vavr.Tuple6;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Created by tao.zeng on 2020-01-10.
 *
 * @Value("${spring.redis-db2.database}") int database,
 * @Value("${spring.redis-db2.host}") String host,
 * @Value("${spring.redis-db2.port}") int port,
 * @Value("${spring.redis-db2.password}") String password,
 * @Value("${spring.redis-db2.timeout}") long timeout,
 * @Value("${spring.redis-db2.lettuce.pool.max-active}") int maxActive,
 * @Value("${spring.redis-db2.lettuce.pool.max-wait}") int maxWait,
 * @Value("${spring.redis-db2.lettuce.pool.max-idle}") int maxIdle,
 * @Value("${spring.redis-db2.lettuce.pool.min-idle}") int minIdle
 */
@EnableCaching
@Configuration
public class RedisConfigure {

    @Bean
    public RedisTemplate redisTemplateDB2(Tuple6<RedisStandaloneConfiguration, Long, Integer, Integer, Integer, Integer> redisConfigurationDB2) {

        Long timeout = redisConfigurationDB2._2();

        int maxActive = redisConfigurationDB2._3(),
                maxWait = redisConfigurationDB2._4(),
                maxIdle = redisConfigurationDB2._5(),
                minIdle = redisConfigurationDB2._6();


        /* ========= 基本配置 ========= */
        RedisStandaloneConfiguration standaloneConfiguration = redisConfigurationDB2._1();


        /* ========= 连接池通用配置 ========= */
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);

        /* ========= jedis pool ========= */
        // jedisConnectionFactory(standaloneConfiguration, genericObjectPoolConfig, timeout);

        /* ========= lettuce pool ========= */
        LettuceConnectionFactory connectionFactory = lettuceConnectionFactory(standaloneConfiguration, genericObjectPoolConfig, timeout);

        // 连接池初始化
        connectionFactory.afterPropertiesSet();

        // 创建 RedisTemplate
        return createRedisTemplate(connectionFactory);
    }

    /**
     * lettuceConnectionFactory
     *
     * @param standaloneConfiguration Redis标准配置
     * @param genericObjectPoolConfig Redis通用配置
     * @param timeout                 超时时间
     * @return
     */
    private LettuceConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration standaloneConfiguration, GenericObjectPoolConfig genericObjectPoolConfig, long timeout) {
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(Duration.ofSeconds(timeout));
        return new LettuceConnectionFactory(standaloneConfiguration, builder.build());
    }

    /**
     * jedisConnectionFactory
     *
     * @param standaloneConfiguration Redis标准配置
     * @param genericObjectPoolConfig Redis通用配置
     * @param timeout                 超时时间
     * @return
     */
    private JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration standaloneConfiguration, GenericObjectPoolConfig genericObjectPoolConfig, long timeout) {
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        builder.connectTimeout(Duration.ofSeconds(timeout));
        builder.usePooling();
        builder.poolConfig(genericObjectPoolConfig);
        return new JedisConnectionFactory(standaloneConfiguration, builder.build());
    }

    /**
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return createRedisTemplate(redisConnectionFactory);
    }

    /**
     * json 实现 redisTemplate
     * <p>
     * 该方法不能加 @Bean 否则不管如何调用，RedisConnectionFactory 都会是默认配置
     *
     * @param redisConnectionFactory
     * @return
     */
    private RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 自定义Redis的配置加载
     *
     * @param database
     * @param password
     * @param host
     * @param port
     * @param timeout
     * @param maxActive
     * @param maxWait
     * @param maxIdle
     * @param minIdle
     * @return
     */
    @Bean
    public Tuple6<RedisStandaloneConfiguration, Long, Integer, Integer, Integer, Integer>
    redisConfigurationDB2(@Value("${spring.redis-db2.database}") int database,
                          @Value("${spring.redis-db2.password}") String password,
                          @Value("${spring.redis-db2.host}") String host,
                          @Value("${spring.redis-db2.port}") int port,
                          @Value("${spring.redis-db2.timeout}") long timeout,
                          @Value("${spring.redis-db2.lettuce.pool.max-active}") int maxActive,
                          @Value("${spring.redis-db2.lettuce.pool.max-wait}") int maxWait,
                          @Value("${spring.redis-db2.lettuce.pool.max-idle}") int maxIdle,
                          @Value("${spring.redis-db2.lettuce.pool.min-idle}") int minIdle) {

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setDatabase(database);
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);

        if (StringUtils.isNotEmpty(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            standaloneConfiguration.setPassword(redisPassword);
        }

        return Tuple.of(standaloneConfiguration, timeout, maxActive, maxWait, maxIdle, minIdle);

    }
}
