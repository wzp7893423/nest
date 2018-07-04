package com.nest.admin.core.mongo.core;

import java.util.List;

/**
 * Created by wzp on 2018/6/15.
 */
public interface MongoEntityOperations {

    <T> List<T> findAll(Class<T> domainType);

}
