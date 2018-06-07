package com.nest.admin.core.fromwork.mongo.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.*;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

/**
 * Created by wzp on 2018/6/4.
 */
public class MonogRepositoryRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private @SuppressWarnings("null") @Nonnull
    ResourceLoader resourceLoader;
    private @SuppressWarnings("null") @Nonnull
    Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null!");

        // Guard against calls for sub-classes
        if (annotationMetadata.getAnnotationAttributes(getAnnotation().getName()) == null) {
            return;
        }

        CustomAnnotationRepositoryConfigurationSource configurationSource = new CustomAnnotationRepositoryConfigurationSource(
                annotationMetadata, getAnnotation(), resourceLoader, environment, registry);

        RepositoryConfigurationExtension extension = getExtension();
        RepositoryConfigurationUtils.exposeRegistration(extension, registry, configurationSource);

        RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(configurationSource, resourceLoader,
                environment);

        delegate.registerRepositoriesIn(registry, extension);
    }

    protected Class <? extends Annotation> getAnnotation() {
        return EnableMongoRepository.class;
    }


    protected RepositoryConfigurationExtension getExtension() {
        return new MongoRepositoryConfigurationExtension();
    }
}
