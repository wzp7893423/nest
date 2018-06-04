package com.nest.admin.core.fromwork.mongo.config;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * Created by wzp on 2018/6/4.
 */
public class MonogRepositoryRegistrar  extends RepositoryBeanDefinitionRegistrarSupport {
    @Override
    protected Class <? extends Annotation> getAnnotation() {
        return EnableMongoRepository.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new MongoRepositoryConfigurationExtension();
    }
}
