package com.iogogogo.vertica.persistent.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * 配置使用 HikariCP 连接池
 * <p>
 * Created by tao.zeng on 2019-03-12.
 */
public class HikariCPDataSourceFactory extends UnpooledDataSourceFactory {

    public HikariCPDataSourceFactory() {
        this.dataSource = new HikariDataSource();
    }
}
