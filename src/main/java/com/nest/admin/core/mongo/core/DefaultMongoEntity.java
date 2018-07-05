package com.nest.admin.core.mongo.core;

/**
 * Created by wzp on 2018/6/29.
 */
public class DefaultMongoEntity<T> implements MongoEntity<T> {

    private MongoEntityInformation mongoEntityInformation;

    public DefaultMongoEntity(MongoEntityInformation mongoEntityInformation) {
        this.mongoEntityInformation = mongoEntityInformation;
    }

    @Override
    public Class<T> getDomainType() {
        return (Class <T>) this.mongoEntityInformation.getDomainType();
    }

    @Override
    public String getDataBaseName(){
        return mongoEntityInformation.getDataBaseName();
    }
}
