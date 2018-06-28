package com.nest.admin.core.fromwork.mongo.support;

import com.nest.admin.core.fromwork.mongo.core.CustomRepositoryMetadata;
import com.nest.admin.core.fromwork.mongo.core.MongoEntity;
import com.nest.admin.core.fromwork.mongo.core.MongoEntityInformation;
import com.nest.admin.core.fromwork.mongo.core.MongoEntityOperations;
import com.nest.admin.core.fromwork.mongo.repository.MongoRepository;
import com.nest.admin.core.fromwork.mongo.repository.SimpleMongoRepository;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.transaction.interceptor.TransactionalProxy;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryFactory implements BeanClassLoaderAware, BeanFactoryAware {

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    private MongoEntityOperations mongoEntityOperations;

    public MongoRepositoryFactory(MongoEntityOperations mongoEntityOperations) {
        this.mongoEntityOperations = mongoEntityOperations;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected Object getTargetRepository(MongoEntityInformation mongoEntityInformation) {
        MongoEntity mongoEntity = null;
        return new SimpleMongoRepository <>(mongoEntityOperations, mongoEntity);
    }

    @SuppressWarnings({"unchecked"})
    public <T> T getRepository(Class <? extends T> repositoryInterface, RepositoryComposition.RepositoryFragments repositoryFragmentsToUse) {
        RepositoryMetadata repositoryMetadata = new CustomRepositoryMetadata(repositoryInterface);
        MongoEntityInformation mongoEntityInformation = MongoEntityInformation.getMongoEntityInfomation(repositoryMetadata, repositoryFragmentsToUse);
        ProxyFactory result = new ProxyFactory();
        result.setTarget(getTargetRepository(mongoEntityInformation));
        result.setInterfaces(repositoryInterface, MongoRepository.class, TransactionalProxy.class);
        return (T) result.getProxy(classLoader);
    }
}
