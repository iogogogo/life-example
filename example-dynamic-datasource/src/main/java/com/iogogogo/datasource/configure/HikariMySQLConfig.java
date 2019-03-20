package com.iogogogo.datasource.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tao.zeng on 2019-03-20.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mysql.hikari")
public class HikariMySQLConfig {

    private String poolName;

    private boolean autoCommit;

    private long connectionTimeout;

    private long idleTimeout;

    private long maxLifetime;

    private int maximumPoolSize;

    private int minimumIdle;

    private String connectionTestQuery;
}
