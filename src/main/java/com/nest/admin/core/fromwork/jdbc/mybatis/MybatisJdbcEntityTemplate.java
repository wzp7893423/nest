package com.nest.admin.core.fromwork.jdbc.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.conversion.AggregateChange;
import org.springframework.data.jdbc.core.conversion.Interpreter;
import org.springframework.data.jdbc.core.conversion.JdbcEntityDeleteWriter;
import org.springframework.data.jdbc.core.conversion.JdbcEntityWriter;
import org.springframework.data.jdbc.mapping.event.*;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentEntityInformation;
import org.springframework.data.jdbc.mybatis.MyBatisContext;
import org.springframework.data.jdbc.mybatis.NamespaceStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wzp on 2018/5/18.
 */
public class MybatisJdbcEntityTemplate implements MybatisEntityOperations {
    private final ApplicationEventPublisher publisher;
    private final JdbcMappingContext context;
    private final Interpreter interpreter;

    private final JdbcEntityWriter jdbcEntityWriter;
    private final JdbcEntityDeleteWriter jdbcEntityDeleteWriter;

    private final DataAccessStrategy accessStrategy;

    private final SqlSession sqlSession;


    private NamespaceStrategy namespaceStrategy = NamespaceStrategy.DEFAULT_INSTANCE;

    public MybatisJdbcEntityTemplate(ApplicationEventPublisher publisher, JdbcMappingContext context,
                                     DataAccessStrategy dataAccessStrategy, SqlSession sqlSession) {

        this.publisher = publisher;
        this.context = context;
        this.jdbcEntityWriter = new JdbcEntityWriter(context);
        this.jdbcEntityDeleteWriter = new JdbcEntityDeleteWriter(context);
        this.accessStrategy = dataAccessStrategy;
        this.interpreter = new MybatisJdbcInterprete(context, accessStrategy);
        this.sqlSession = sqlSession;
    }

    @Override
    public <T> void save(T instance, Class <T> domainType) {

        JdbcPersistentEntityInformation <T, ?> entityInformation = context
                .getRequiredPersistentEntityInformation(domainType);

        AggregateChange change = createChange(instance);

        publisher.publishEvent(new BeforeSaveEvent( //
                Identifier.ofNullable(entityInformation.getId(instance)), //
                instance, //
                change //
        ));

        change.executeWith(interpreter);

        publisher.publishEvent(new AfterSaveEvent( //
                Identifier.of(entityInformation.getId(instance)), //
                instance, //
                change //
        ));
    }

    @Override
    public long count(Class <?> domainType) {
        return accessStrategy.count(domainType);
    }

    @Override
    public <T> T findById(Object id, Class <T> domainType) {

        T entity = accessStrategy.findById(id, domainType);
        if (entity != null) {
            publishAfterLoad(id, entity);
        }
        return entity;
    }

    @Override
    public <T> boolean existsById(Object id, Class <T> domainType) {
        return accessStrategy.existsById(id, domainType);
    }

    @Override
    public <T> Iterable <T> findAll(Class <T> domainType) {

        Iterable <T> all = accessStrategy.findAll(domainType);
        publishAfterLoad(all);
        return all;
    }

    @Override
    public <T> Iterable <T> findAllById(Iterable <?> ids, Class <T> domainType) {

        Iterable <T> allById = accessStrategy.findAllById(ids, domainType);
        publishAfterLoad(allById);
        return allById;
    }

    @Override
    public <S> void delete(S entity, Class <S> domainType) {

        JdbcPersistentEntityInformation <S, ?> entityInformation = context
                .getRequiredPersistentEntityInformation(domainType);
        deleteTree(entityInformation.getRequiredId(entity), entity, domainType);
    }

    @Override
    public <S> void deleteById(Object id, Class <S> domainType) {
        deleteTree(id, null, domainType);
    }

    @Override
    public void deleteAll(Class <?> domainType) {

        AggregateChange change = createDeletingChange(domainType);
        change.executeWith(interpreter);
    }

    private void deleteTree(Object id, Object entity, Class <?> domainType) {

        AggregateChange change = createDeletingChange(id, entity, domainType);

        Identifier.Specified specifiedId = Identifier.of(id);
        Optional <Object> optionalEntity = Optional.ofNullable(entity);
        publisher.publishEvent(new BeforeDeleteEvent(specifiedId, optionalEntity, change));

        change.executeWith(interpreter);

        publisher.publishEvent(new AfterDeleteEvent(specifiedId, optionalEntity, change));
    }

    @SuppressWarnings("unchecked")
    private <T> AggregateChange createChange(T instance) {

        AggregateChange <?> aggregateChange = new AggregateChange(AggregateChange.Kind.SAVE, instance.getClass(), instance);
        jdbcEntityWriter.write(instance, aggregateChange);
        return aggregateChange;
    }

    @SuppressWarnings("unchecked")
    private AggregateChange createDeletingChange(Object id, Object entity, Class <?> domainType) {

        AggregateChange <?> aggregateChange = new AggregateChange(AggregateChange.Kind.DELETE, domainType, entity);
        jdbcEntityDeleteWriter.write(id, aggregateChange);
        return aggregateChange;
    }

    private AggregateChange createDeletingChange(Class <?> domainType) {

        AggregateChange <?> aggregateChange = new AggregateChange <>(AggregateChange.Kind.DELETE, domainType, null);
        jdbcEntityDeleteWriter.write(null, aggregateChange);
        return aggregateChange;
    }

    @SuppressWarnings("unchecked")
    private <T> void publishAfterLoad(Iterable <T> all) {

        for (T e : all) {
            publishAfterLoad(context.getRequiredPersistentEntityInformation((Class <T>) e.getClass()).getRequiredId(e), e);
        }
    }

    private <T> void publishAfterLoad(Object id, T entity) {
        publisher.publishEvent(new AfterLoadEvent(Identifier.of(id), entity));
    }

    @Override
    public <T> List <T> selectList(String namespance, Map <String, Object> params, Class <T> domainType) {
        return sqlSession.selectList(namespaceStrategy.getNamespace(domainType) + "." + namespance, new MyBatisContext(null, null, domainType, params));
    }


}
