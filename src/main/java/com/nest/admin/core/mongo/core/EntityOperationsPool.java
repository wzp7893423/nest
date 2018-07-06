package com.nest.admin.core.mongo.core;

/**
 * Created by wzp on 2018/7/6.
 */
public interface EntityOperationsPool {

    public MongoEntityOperations getMongoEntityOperation();

    public MongoEntityOperations getMongoEntityOperation(String dataBaseName);

}
