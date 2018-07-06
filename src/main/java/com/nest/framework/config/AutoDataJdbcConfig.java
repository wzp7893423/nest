package com.nest.framework.config;

import com.nest.framework.orm.annotation.TableName;
import com.nest.framework.orm.jdbc.config.JdbcDataProperties;
import com.nest.framework.orm.jdbc.core.DataSoureFactory;
import com.nest.framework.orm.jdbc.mybatis.support.MybatisJdbcRepositoryFactoryBean;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentProperty;
import org.springframework.data.jdbc.mapping.model.NamingStrategy;
import org.springframework.data.jdbc.mybatis.MyBatisDataAccessStrategy;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.util.ParsingUtils;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * Created by wzp on 2018/5/14.
 */
@Configuration
@EnableConfigurationProperties(JdbcDataProperties.class)
@EnableJdbcRepositories(basePackages = {"com.nest.function"}, repositoryFactoryBeanClass = MybatisJdbcRepositoryFactoryBean.class)
public class AutoDataJdbcConfig {

    private final Logger logger = LoggerFactory.getLogger(AutoDataJdbcConfig.class);

    @Bean
    public DataSource dataSource(JdbcDataProperties jdbcDataProperties) {
        return DataSoureFactory.getDruidDataSource(jdbcDataProperties);
    }

    @Bean
    @Primary
    DataAccessStrategy defaultDataAccessStrategy(JdbcMappingContext context, SqlSession sqlSession) {
        return MyBatisDataAccessStrategy.createCombinedAccessStrategy(context, sqlSession);
    }

    //    @Bean
    public NamingStrategy namingStrategy() {
        String delimiter = "_";
        return new NamingStrategy() {
            @Override
            public String getTableName(Class <?> type) {
                Optional <TableName> tableName = Optional.ofNullable(type.getAnnotation(TableName.class));
                if (tableName.isPresent()) {
                    return tableName.get().value();
//                    return ParsingUtils.reconcatenateCamelCase(typeAlias.get().value(), delimiter);
                } else {
                    return ParsingUtils.reconcatenateCamelCase(NamingStrategy.super.getTableName(type), delimiter);
                }
            }

            /**
             * Look up the {@link JdbcPersistentProperty}'s name after converting to separated word using with {@code delimiter}.
             */
            @Override
            public String getColumnName(JdbcPersistentProperty property) {
                return ParsingUtils.reconcatenateCamelCase(NamingStrategy.super.getColumnName(property), delimiter);
            }

            /**
             * Return the value that adding {@code delimiter} + 'key' for returned value of {@link #getReverseColumnName}.
             */
            @Override
            public String getKeyColumn(JdbcPersistentProperty property) {
                return getReverseColumnName(property) + delimiter + "key";
            }
        };
    }
}
