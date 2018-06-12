package com.nest.admin.core.fromwork.jdbc.mybatis.support;

import com.nest.admin.core.fromwork.jdbc.mapper.CustomEntityRowMapperMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.SqlGeneratorSource;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.repository.RowMapperMap;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created by wzp on 2018/5/17.
 */
public class MybatisJdbcRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> //
        extends TransactionalRepositoryFactoryBeanSupport<T, S, ID> implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    private JdbcMappingContext mappingContext;
    private DataAccessStrategy dataAccessStrategy;
    private RowMapperMap rowMapperMap = new CustomEntityRowMapperMap <>();
    private SqlSession sqlSession;

    MybatisJdbcRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {

        super.setApplicationEventPublisher(publisher);
        this.publisher = publisher;
    }

    @Override
    protected RepositoryFactorySupport doCreateRepositoryFactory() {

        MybatisJdbcRepositoryFactory jdbcRepositoryFactory = new MybatisJdbcRepositoryFactory(publisher, mappingContext,
                dataAccessStrategy,sqlSession);
        jdbcRepositoryFactory.setRowMapperMap(rowMapperMap);

        return jdbcRepositoryFactory;
    }

    @Autowired
    protected void setMappingContext(JdbcMappingContext mappingContext) {

        super.setMappingContext(mappingContext);
        this.mappingContext = mappingContext;
    }

    @Autowired(required = false)
    public void setDataAccessStrategy(DataAccessStrategy dataAccessStrategy) {
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Autowired(required = false)
    public void setRowMapperMap(RowMapperMap rowMapperMap) {
        this.rowMapperMap = rowMapperMap;
    }
    @Autowired
    protected void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Override
    public void afterPropertiesSet() {

        Assert.state(this.mappingContext != null, "MappingContext is required and must not be null!");

        if (dataAccessStrategy == null) {

            dataAccessStrategy = new DefaultDataAccessStrategy( //
                    new SqlGeneratorSource(mappingContext), //
                    mappingContext);
        }
        super.afterPropertiesSet();
    }
}
