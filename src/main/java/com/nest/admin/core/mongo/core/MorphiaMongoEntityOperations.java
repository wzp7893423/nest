package com.nest.admin.core.mongo.core;

import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by wzp on 2018/6/28.
 */
public class MorphiaMongoEntityOperations implements MongoEntityOperations {

    private final Datastore datastore;

    public MorphiaMongoEntityOperations(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public <T> List <T> findAll(Class <T> domainType, MongoEntity <T> mongoEntity) {
        return null;
//        return (List <T>) this.getDatastore(mongoEntity.getDataBaseName()).createQuery(domainType).asList();
    }

    public <T> List <T> findAll(Class<T> domainType,MongoEntity<T> mongoEntity,Options options){
        //
        return  null;
    }
}
