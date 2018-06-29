package com.nest.admin.core.fromwork.mongo.core;

/**
 * Created by wzp on 2018/6/29.
 */
public class DefaultMongoEntity<T> implements MongoEntity<T> {

    private MongoEntityInformation mongoEntityInformation;

    public DefaultMongoEntity(MongoEntityInformation mongoEntityInformation) {
        this.mongoEntityInformation = mongoEntityInformation;
    }

    @Override
    public T getDomainType() {
        return (T) this.mongoEntityInformation.getDomainType();
    }
}
