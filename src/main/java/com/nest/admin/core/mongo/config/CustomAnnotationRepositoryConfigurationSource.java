package com.nest.admin.core.mongo.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.util.Streamable;

import java.lang.annotation.Annotation;

/**
 * Created by wzp on 2018/6/7.
 */
public class CustomAnnotationRepositoryConfigurationSource extends AnnotationRepositoryConfigurationSource {
    private final Environment environment;
    private final BeanDefinitionRegistry registry;

    public CustomAnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class <? extends Annotation> annotation, ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry) {
        super(metadata, annotation, resourceLoader, environment, registry);
        this.environment = environment;
        this.registry = registry;
    }

    public Streamable <BeanDefinition> getCandidates(ResourceLoader loader) {
        CustomRepositoryComponentProvider scanner = new CustomRepositoryComponentProvider(getIncludeFilters(), registry);
        scanner.setConsiderNestedRepositoryInterfaces(shouldConsiderNestedRepositories());
        scanner.setEnvironment(environment);
        scanner.setResourceLoader(loader);

        getExcludeFilters().forEach(it -> scanner.addExcludeFilter(it));

        return Streamable.of(() -> getBasePackages().stream()//
                .flatMap(it -> scanner.findCandidateComponents(it).stream()));
    }

}
