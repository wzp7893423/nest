package com.nest.framework.orm.jdbc.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.nest.framework.orm.jdbc.config.JdbcDataProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by wzp on 2018/7/6.
 */
public class DataSoureFactory {

    private final static Logger logger = LoggerFactory.getLogger(DataSoureFactory.class);

    public static DataSource getDruidDataSource(JdbcDataProperties jdbcDataProperties){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(jdbcDataProperties.getUrl());
        datasource.setUsername(jdbcDataProperties.getUsername());
        datasource.setPassword(jdbcDataProperties.getPassword());
        datasource.setDriverClassName(jdbcDataProperties.getDriverClassName());
        datasource.setInitialSize(jdbcDataProperties.getInitialSize());
        datasource.setMinIdle(jdbcDataProperties.getMinIdle());
        datasource.setMaxActive(jdbcDataProperties.getMaxActive());
        datasource.setMaxWait(jdbcDataProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(jdbcDataProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(jdbcDataProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(jdbcDataProperties.getValidationQuery());
        datasource.setTestWhileIdle(jdbcDataProperties.isTestWhileIdle());
        datasource.setTestOnBorrow(jdbcDataProperties.isTestOnBorrow());
        datasource.setTestOnReturn(jdbcDataProperties.isTestOnReturn());
        datasource.setPoolPreparedStatements(jdbcDataProperties.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(jdbcDataProperties.getMaxPoolPreparedStatementPerConnectionSize());
        datasource.setConnectionProperties(jdbcDataProperties.getConnectionProperties());
        try {
            datasource.setFilters(jdbcDataProperties.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return datasource;
    }
}
