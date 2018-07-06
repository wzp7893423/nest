package com.nest.framework.orm.mongo.config;

import com.nest.framework.orm.mongo.core.CustomRepositoryMetadata;
import com.nest.framework.orm.mongo.support.MongoRepositoryFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wzp on 2018/6/4.
 */
public class MongoRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryConfigurationExtensionSupport.class);
    private static final String CLASS_LOADING_ERROR = "%s - Could not load type %s using class loader %s.";
    private static final String MULTI_STORE_DROPPED = "Spring Data {} - Could not safely identify store assignment for repository candidate {}.";

    @Override
    protected String getModulePrefix() {
        return "MONGO";
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return MongoRepositoryFactoryBean.class.getName();
    }

    @Override
    public <T extends RepositoryConfigurationSource> Collection <RepositoryConfiguration <T>> getRepositoryConfigurations(
            T configSource, ResourceLoader loader, boolean strictMatchesOnly) {

        Assert.notNull(configSource, "ConfigSource must not be null!");
        Assert.notNull(loader, "Loader must not be null!");

        Set <RepositoryConfiguration <T>> result = new HashSet <>();

        for (BeanDefinition candidate : configSource.getCandidates(loader)) {

            RepositoryConfiguration <T> configuration = getRepositoryConfiguration(candidate, configSource);
            Class <?> repositoryInterface = loadRepositoryInterface(configuration,
                    super.getConfigurationInspectionClassLoader(loader));

            if (repositoryInterface == null) {
                result.add(configuration);
                continue;
            }

            RepositoryMetadata metadata = CustomRepositoryMetadata.getMetadata(repositoryInterface);

            if (!super.useRepositoryConfiguration(metadata)) {
                continue;
            }

            if (!strictMatchesOnly || configSource.usesExplicitFilters()) {
                result.add(configuration);
                continue;
            }

            if (isStrictRepositoryCandidate(metadata)) {
                result.add(configuration);
            }
        }
        return result;
    }

    private Class <?> loadRepositoryInterface(RepositoryConfiguration <?> configuration,
                                              @Nullable ClassLoader classLoader) {

        String repositoryInterface = configuration.getRepositoryInterface();

        try {
            return org.springframework.util.ClassUtils.forName(repositoryInterface, classLoader);
        } catch (ClassNotFoundException | LinkageError e) {
            LOGGER.warn(String.format(CLASS_LOADING_ERROR, getModuleName(), repositoryInterface, classLoader), e);
        }

        return null;
    }

    protected boolean isStrictRepositoryCandidate(RepositoryMetadata metadata) {

        Collection <Class <?>> types = getIdentifyingTypes();
        Class <?> repositoryInterface = metadata.getRepositoryInterface();

        for (Class <?> type : types) {
            if (type.isAssignableFrom(repositoryInterface)) {
                return true;
            }
        }

        Class <?> domainType = metadata.getDomainType();
        Collection <Class <? extends Annotation>> annotations = getIdentifyingAnnotations();

        if (annotations.isEmpty()) {
            return true;
        }

        for (Class <? extends Annotation> annotationType : annotations) {
            if (AnnotationUtils.findAnnotation(domainType, annotationType) != null) {
                return true;
            }
        }

        LOGGER.info(MULTI_STORE_DROPPED, getModuleName(), repositoryInterface);

        return false;
    }
}
