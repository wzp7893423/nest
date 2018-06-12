package com.nest.admin.core.fromwork.mongo.support;

import com.nest.admin.core.fromwork.mongo.MongoRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.query.DefaultEvaluationContextProvider;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.util.Lazy;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactoryBean<T extends MongoRepository> extends AbstractMongoRepositorySupport{


    MongoRepositoryFactoryBean(Class<? extends T> repositoryInterface){
        super(repositoryInterface);
    }


    protected MongoRepositoryFactory createRepositoryFactory() {
        return new MongoRepositoryFactory();
    }
}
