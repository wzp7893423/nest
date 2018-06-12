package com.nest.admin.core.fromwork.mongo.support;

import com.nest.admin.core.fromwork.jdbc.mybatis.MybatisJdbcEntityTemplate;
import com.nest.admin.core.fromwork.jdbc.mybatis.MybatisSimpleRepository;
import com.nest.admin.core.fromwork.mongo.MongoRepository;
import com.nest.admin.core.fromwork.mongo.SimpleMongoRepository;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentEntityInformation;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.transaction.interceptor.TransactionalProxy;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactory implements BeanClassLoaderAware, BeanFactoryAware {

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

    protected Object getTargetRepository() {
        return new SimpleMongoRepository<>();
    }
    @SuppressWarnings({ "unchecked" })
    public <T> T getRepository(Class <? extends T> repositoryInterface){
        ProxyFactory result = new ProxyFactory();
        result.setTarget(getTargetRepository());
        result.setInterfaces(repositoryInterface, MongoRepository.class, TransactionalProxy.class);
        return (T) result.getProxy(classLoader);
    }
}
