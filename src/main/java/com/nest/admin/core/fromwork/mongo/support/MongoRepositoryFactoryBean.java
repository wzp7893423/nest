package com.nest.admin.core.fromwork.mongo.support;

import com.nest.admin.core.fromwork.mongo.MongoRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.util.Lazy;
import org.springframework.util.Assert;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactoryBean<T extends MongoRepository> implements InitializingBean,FactoryBean,
        BeanFactoryAware, ApplicationEventPublisherAware {

    private BeanFactory beanFactory;

    private Lazy<T> repository;

    private  boolean lazyInit;

    private MongoRepositoryFactory factory;

    private final  Class<? extends T> repositoryInterface;

    MongoRepositoryFactoryBean(Class<? extends T> repositoryInterface){
        Assert.isNull(repositoryInterface,"repositoryInterface is null");
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        return repository.get();
    }

    @Override
    public Class <?> getObjectType() {
        return repositoryInterface;
    }

    private MongoRepositoryFactory createRepositoryFactory() {
        return new MongoRepositoryFactory();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        factory = createRepositoryFactory();
        repository = Lazy.of(()->this.factory.getRepository());
        if (!lazyInit){
            repository.get();
        }
    }
}
