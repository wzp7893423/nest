package com.nest.admin.core.mongo.core;

/**
 * Created by wzp on 2018/7/6.
 */
public class MongoContext {

    private final MongoEntityOperations mongoEntityOperations;

    public MongoContext(MongoEntityOperations mongoEntityOperations) {
        this.mongoEntityOperations = mongoEntityOperations;
    }

    public MongoEntityOperations getMongoEntityOperations() {
        return mongoEntityOperations;
    }

}
