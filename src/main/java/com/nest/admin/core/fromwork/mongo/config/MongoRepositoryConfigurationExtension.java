package com.nest.admin.core.fromwork.mongo.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.config.*;

import java.util.Collection;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport {

    @Override
    protected String getModulePrefix() {
        return null;
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return null;
    }
}
