package com.iogogogo.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import javax.sql.DataSource;

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

    public DataSource injection(DataSource dataSource) {
        HikariDataSource hikariDataSource = null;
        if (dataSource instanceof HikariDataSource) {
            // 连接池配置
            hikariDataSource = (HikariDataSource) dataSource;
            hikariDataSource.setPoolName(this.getPoolName());
            hikariDataSource.setAutoCommit(this.isAutoCommit());
            hikariDataSource.setConnectionTestQuery(this.getConnectionTestQuery());
            hikariDataSource.setIdleTimeout(this.getIdleTimeout());
            hikariDataSource.setConnectionTimeout(this.getConnectionTimeout());
            hikariDataSource.setMaximumPoolSize(this.getMaximumPoolSize());
            hikariDataSource.setMaxLifetime(this.getMaxLifetime());
            hikariDataSource.setMinimumIdle(this.getMinimumIdle());
        }
        return hikariDataSource == null ? dataSource : hikariDataSource;
    }
}
