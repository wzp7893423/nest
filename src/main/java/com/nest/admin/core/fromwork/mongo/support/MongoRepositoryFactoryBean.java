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

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactoryBean<T extends MongoRepository> implements InitializingBean,FactoryBean,
        BeanFactoryAware, ApplicationEventPublisherAware {

    private BeanFactory beanFactory;

    private Lazy<T> repository;

    private  boolean lazyInit;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class <?> getObjectType() {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (lazyInit){
            repository.get();
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }
}
