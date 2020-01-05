package com.iogogogo.datasource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.iogogogo.datasource.config.HikariVerticaConfig;
import com.zaxxer.hikari.HikariDataSource;
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
@MapperScan(basePackages = "com.iogogogo.mapper.vertica", sqlSessionTemplateRef = "verticaSqlSessionTemplate")
public class VerticaDataSourceConfigure {

    private HikariVerticaConfig verticaConfig;

    public VerticaDataSourceConfigure(HikariVerticaConfig verticaConfig) {
        this.verticaConfig = verticaConfig;
    }

    @Bean(name = "verticaDataSource")
    @ConfigurationProperties("spring.datasource.vertica")
    public DataSource vertica() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return verticaConfig.injection(dataSource);
    }

    @Bean(name = "verticaSqlSessionFactory")
    public SqlSessionFactory verticaSqlSessionFactory(@Qualifier("verticaDataSource") DataSource dataSource) throws Exception {
        // MyBatis-Plus使用MybatisSqlSessionFactoryBean  MyBatis直接使用SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        // 给MyBatis-Plus注入数据源
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "verticaTransactionManager")
    public DataSourceTransactionManager verticaTransactionManager(@Qualifier("verticaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "verticaSqlSessionTemplate")
    public SqlSessionTemplate verticaSqlSessionTemplate(@Qualifier("verticaSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
