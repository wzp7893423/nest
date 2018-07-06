package com.nest.framework.orm.mongo.support;

import com.nest.framework.orm.mongo.core.EntityOperationsPool;
import com.nest.framework.orm.mongo.core.MongoEntityOperations;
import com.nest.framework.orm.mongo.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactoryBean<T extends MongoRepository> extends AbstractMongoRepositorySupport {

    private EntityOperationsPool entityOperationsPool;

    @Autowired
    public void setEntityOperationsPool(EntityOperationsPool entityOperationsPool) {
        this.entityOperationsPool = entityOperationsPool;
    }

    MongoRepositoryFactoryBean(Class <? extends T> repositoryInterface) {
        super(repositoryInterface);
    }


    protected MongoRepositoryFactory createRepositoryFactory() {
        return new MongoRepositoryFactory(entityOperationsPool);
    }


}
