package com.iogogogo.datasource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.iogogogo.datasource.config.HikariMySQLConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by tao.zeng on 2019-03-19.
 */
@Configuration
@MapperScan(basePackages = "com.iogogogo.mapper.mysql", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class MySQLDataSourceConfigure {

    private HikariMySQLConfig mysqlConfig;

    public MySQLDataSourceConfigure(HikariMySQLConfig mysqlConfig) {
        this.mysqlConfig = mysqlConfig;
    }

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSource mysql() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return mysqlConfig.injection(dataSource);
    }

    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        // MyBatis-Plus使用MybatisSqlSessionFactoryBean  MyBatis直接使用SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        // 给MyBatis-Plus注入数据源
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
