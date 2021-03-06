package com.nest.framework.orm.mongo.repository;

import com.nest.framework.orm.mongo.core.MongoEntity;
import com.nest.framework.orm.mongo.core.MongoEntityOperations;

import java.util.List;

/**
 * Created by wzp on 2018/6/4.
 */
public class SimpleMongoRepository<T> implements MongoRepository <T> {

    private final MongoEntityOperations mongoEntityOperations;

    private final MongoEntity <T> mongoEntity;

    public SimpleMongoRepository(MongoEntityOperations mongoEntityOperations, MongoEntity mongoEntity) {
        this.mongoEntityOperations = mongoEntityOperations;
        this.mongoEntity = mongoEntity;
    }

    @Override
    public List<T> findALl() {
//        return mongoEntityOperations.findAll();
        return mongoEntityOperations.findAll(mongoEntity.getDomainType(),mongoEntity);
    }
}
