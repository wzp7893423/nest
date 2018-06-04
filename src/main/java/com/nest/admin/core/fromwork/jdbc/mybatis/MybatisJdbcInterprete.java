package com.nest.admin.core.fromwork.jdbc.mybatis;

import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.conversion.DbAction;
import org.springframework.data.jdbc.core.conversion.Interpreter;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentEntity;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzp on 2018/5/18.
 */
class MybatisJdbcInterprete implements Interpreter {
    private final JdbcMappingContext context;
    private final DataAccessStrategy accessStrategy;

    MybatisJdbcInterprete(JdbcMappingContext context, DataAccessStrategy accessStrategy) {

        this.context = context;
        this.accessStrategy = accessStrategy;
    }

    @Override
    public <T> void interpret(DbAction.Insert<T> insert) {
        accessStrategy.insert(insert.getEntity(), insert.getEntityType(), createAdditionalColumnValues(insert));
    }

    @Override
    public <T> void interpret(DbAction.Update<T> update) {
        accessStrategy.update(update.getEntity(), update.getEntityType());
    }

    @Override
    public <T> void interpret(DbAction.Delete<T> delete) {

        if (delete.getPropertyPath() == null) {
            accessStrategy.delete(delete.getRootId(), delete.getEntityType());
        } else {
            accessStrategy.delete(delete.getRootId(), delete.getPropertyPath().getPath());
        }
    }

    @Override
    public <T> void interpret(DbAction.DeleteAll<T> delete) {

        if (delete.getEntityType() == null) {
            accessStrategy.deleteAll(delete.getPropertyPath().getPath());
        } else {
            accessStrategy.deleteAll(delete.getEntityType());
        }
    }

    private <T> Map<String, Object> createAdditionalColumnValues(DbAction.Insert<T> insert) {

        Map<String, Object> additionalColumnValues = new HashMap<>();
        addDependingOnInformation(insert, additionalColumnValues);
        additionalColumnValues.putAll(insert.getAdditionalValues());

        return additionalColumnValues;
    }

    private <T> void addDependingOnInformation(DbAction.Insert<T> insert, Map<String, Object> additionalColumnValues) {

        DbAction dependingOn = insert.getDependingOn();

        if (dependingOn == null) {
            return;
        }

        JdbcPersistentEntity<?> persistentEntity = context.getRequiredPersistentEntity(dependingOn.getEntityType());

        String columnName = getColumnNameForReverseColumn(insert, persistentEntity);

        Object identifier = getIdFromEntityDependingOn(dependingOn, persistentEntity);

        additionalColumnValues.put(columnName, identifier);
    }

    private Object getIdFromEntityDependingOn(DbAction dependingOn, JdbcPersistentEntity<?> persistentEntity) {
        return persistentEntity.getIdentifierAccessor(dependingOn.getEntity()).getIdentifier();
    }

    private <T> String getColumnNameForReverseColumn(DbAction.Insert<T> insert, JdbcPersistentEntity<?> persistentEntity) {

        PropertyPath path = insert.getPropertyPath().getPath();

        Assert.notNull(path, "There shouldn't be an insert depending on another insert without having a PropertyPath.");

        return persistentEntity.getRequiredPersistentProperty(path.getSegment()).getReverseColumnName();
    }
}
