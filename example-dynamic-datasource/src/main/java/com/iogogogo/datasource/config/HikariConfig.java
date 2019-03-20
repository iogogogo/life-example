package com.iogogogo.datasource.config;

import lombok.Data;

/**
 * Created by tao.zeng on 2019-03-20.
 */
@Data
public class HikariConfig {

    private String poolName;

    private boolean autoCommit;

    private long connectionTimeout;

    private long idleTimeout;

    private long maxLifetime;

    private int maximumPoolSize;

    private int minimumIdle;

    private String connectionTestQuery;
}
