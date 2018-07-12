package com.nest.framework.orm.mongo.core;

import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by wzp on 2018/6/28.
 */
public class MorphiaMongoEntityOperations implements MongoEntityOperations {

    private final Datastore datastore;

    public MorphiaMongoEntityOperations(final Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public <T> List <T> findAll(final Class <T> domainType, final MongoEntity <T> mongoEntity) {
        return datastore.find(domainType).asList();
    }

    public <T> List <T> findAll(final  Class<T> domainType,final MongoEntity<T> mongoEntity,final  Options options){
        return  null;
    }
}
