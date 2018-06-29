package com.nest.admin.core.fromwork.mongo.core;

/**
 * Created by wzp on 2018/6/28.
 */
public interface MongoEntity<T> {

    default MongoEntity<T> buildMongoEntity(MongoEntityInformation mongoEntityInformation){
        return new DefaultMongoEntity <>(mongoEntityInformation);
    }

    public T getDomainType();

}
