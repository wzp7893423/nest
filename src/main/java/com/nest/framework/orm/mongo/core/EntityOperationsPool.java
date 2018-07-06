package com.nest.framework.orm.mongo.core;

/**
 * Created by wzp on 2018/7/6.
 */
public interface EntityOperationsPool {

    public MongoEntityOperations getMongoEntityOperation();

    public MongoEntityOperations getMongoEntityOperation(String dataBaseName);

}
