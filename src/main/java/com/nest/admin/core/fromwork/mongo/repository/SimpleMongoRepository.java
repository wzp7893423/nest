package com.nest.admin.core.fromwork.mongo.repository;

import com.nest.admin.core.fromwork.mongo.core.MongoEntity;
import com.nest.admin.core.fromwork.mongo.core.MongoEntityOperations;

/**
 * Created by wzp on 2018/6/4.
 */
public class SimpleMongoRepository<T> implements MongoRepository <T> {

    private MongoEntityOperations mongoEntityOperations;

    private MongoEntity <T> mongoEntity;

    public SimpleMongoRepository(MongoEntityOperations mongoEntityOperations, MongoEntity mongoEntity) {
        this.mongoEntityOperations = mongoEntityOperations;
        this.mongoEntity = mongoEntity;
    }
}
