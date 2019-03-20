package com.iogogogo.datasource.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tao.zeng on 2019-03-20.
 */
@Data
@Configuration
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "spring.datasource.mysql.hikari")
public class HikariMySQLConfig extends HikariConfig {
}
