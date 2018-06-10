package com.nest.admin.core.fromwork.mongo.support;

import com.nest.admin.core.fromwork.mongo.MongoRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactory<T extends MongoRepository> implements BeanClassLoaderAware, BeanFactoryAware {

    private  ClassLoader classLoader;

    private  BeanFactory beanFactory;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
