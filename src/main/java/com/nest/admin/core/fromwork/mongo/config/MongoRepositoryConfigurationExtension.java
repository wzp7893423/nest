package com.nest.admin.core.fromwork.mongo.config;

import com.nest.admin.core.fromwork.mongo.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport {

    @Override
    protected String getModulePrefix() {
        return "MONGO";
    }
    @Override
    public String getRepositoryFactoryBeanClassName() {
        return MongoRepositoryFactoryBean.class.getName();
    }
}
