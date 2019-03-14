package com.sharplook.configure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlus 分页插件配置
 * <p>
 * Created by tao.zeng on 2019/03/08.
 */
@Slf4j
@Configuration
public class MyBatisPlusConfigure {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.info("MyBatisPlus PaginationInterceptor Configure");
        PaginationInterceptor interceptor = new PaginationInterceptor();
        interceptor.setDialectType(DbType.MYSQL.getDb());
        return interceptor;
    }
}
