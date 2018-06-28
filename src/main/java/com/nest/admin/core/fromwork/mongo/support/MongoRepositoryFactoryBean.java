package com.nest.admin.core.fromwork.mongo.support;

import com.nest.admin.core.fromwork.mongo.core.MongoEntityOperations;
import com.nest.admin.core.fromwork.mongo.repository.MongoRepository;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactoryBean<T extends MongoRepository> extends AbstractMongoRepositorySupport {

    private MongoEntityOperations mongoEntityOperations;

    //    @Autowired
    public void setMongoEntityOperations(MongoEntityOperations mongoEntityOperations) {
        this.mongoEntityOperations = mongoEntityOperations;
    }

    MongoRepositoryFactoryBean(Class <? extends T> repositoryInterface) {
        super(repositoryInterface);
    }


    protected MongoRepositoryFactory createRepositoryFactory() {
        return new MongoRepositoryFactory(mongoEntityOperations);
    }


}
