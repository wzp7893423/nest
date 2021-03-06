package com.nest.framework.orm.mongo.config;

import com.nest.framework.orm.mongo.repository.MongoRepository;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.util.ClassUtils;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wzp on 2018/6/7.
 */
public class CustomRepositoryComponentProvider extends ClassPathScanningCandidateComponentProvider {
    private boolean considerNestedRepositoryInterfaces;
    private BeanDefinitionRegistry registry;

    /**
     * Creates a new {@link CustomRepositoryComponentProvider} using the given {@link TypeFilter} to include components to be
     * picked up.
     *
     * @param includeFilters the {@link TypeFilter}s to select repository interfaces to consider, must not be
     *                       {@literal null}.
     */
    public CustomRepositoryComponentProvider(Iterable <? extends TypeFilter> includeFilters, BeanDefinitionRegistry registry) {

        super(false);

        Assert.notNull(includeFilters, "Include filters must not be null!");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");

        this.registry = registry;

        if (includeFilters.iterator().hasNext()) {
            for (TypeFilter filter : includeFilters) {
                addIncludeFilter(filter);
            }
        } else {
            super.addIncludeFilter(new CustomRepositoryComponentProvider.InterfaceTypeFilter(MongoRepository.class));
        }

        addExcludeFilter(new AnnotationTypeFilter(NoRepositoryBean.class));
    }

    /**
     * Custom extension of {@link #addIncludeFilter(TypeFilter)} to extend the added {@link TypeFilter}. For the
     * {@link TypeFilter} handed we'll have two filters registered: one additionally enforcing the
     * {@link RepositoryDefinition} annotation, the other one forcing the extension of {@link MongoRepository}.
     *
     * @see ClassPathScanningCandidateComponentProvider#addIncludeFilter(TypeFilter)
     */
    @Override
    public void addIncludeFilter(TypeFilter includeFilter) {

        List <TypeFilter> filterPlusInterface = new ArrayList <>(2);
        filterPlusInterface.add(includeFilter);
        filterPlusInterface.add(new CustomRepositoryComponentProvider.InterfaceTypeFilter(MongoRepository.class));

        super.addIncludeFilter(new CustomRepositoryComponentProvider.AllTypeFilter(filterPlusInterface));

        List <TypeFilter> filterPlusAnnotation = new ArrayList <>(2);
        filterPlusAnnotation.add(includeFilter);

        super.addIncludeFilter(new CustomRepositoryComponentProvider.AllTypeFilter(filterPlusAnnotation));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#isCandidateComponent(org.springframework.beans.factory.annotation.AnnotatedBeanDefinition)
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {

        boolean isNonRepositoryInterface = !ClassUtils.isGenericRepositoryInterface(beanDefinition.getBeanClassName());
        boolean isTopLevelType = !beanDefinition.getMetadata().hasEnclosingClass();
        boolean isConsiderNestedRepositories = isConsiderNestedRepositoryInterfaces();

        return isNonRepositoryInterface && (isTopLevelType || isConsiderNestedRepositories);
    }

    /**
     * Customizes the repository interface detection and triggers annotation detection on them.
     */
    @Override
    public Set <BeanDefinition> findCandidateComponents(String basePackage) {

        Set <BeanDefinition> candidates = super.findCandidateComponents(basePackage);

        for (BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
        }

        return candidates;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#getRegistry()
     */
    @Nonnull
    @Override
    protected BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * @return the considerNestedRepositoryInterfaces
     */
    public boolean isConsiderNestedRepositoryInterfaces() {
        return considerNestedRepositoryInterfaces;
    }

    /**
     * Controls whether nested inner-class {@link Repository} interface definitions should be considered for automatic
     * discovery. This defaults to {@literal false}.
     *
     * @param considerNestedRepositoryInterfaces
     */
    public void setConsiderNestedRepositoryInterfaces(boolean considerNestedRepositoryInterfaces) {
        this.considerNestedRepositoryInterfaces = considerNestedRepositoryInterfaces;
    }

    /**
     * {@link org.springframework.core.type.filter.TypeFilter} that only matches interfaces. Thus setting this up makes
     * only sense providing an interface type as {@code targetType}.
     *
     * @author Oliver Gierke
     */
    private static class InterfaceTypeFilter extends AssignableTypeFilter {

        /**
         * Creates a new {@link CustomRepositoryComponentProvider.InterfaceTypeFilter}.
         *
         * @param targetType
         */
        public InterfaceTypeFilter(Class <?> targetType) {
            super(targetType);
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.orm.type.filter.AbstractTypeHierarchyTraversingFilter#match(org.springframework.orm.type.classreading.MetadataReader, org.springframework.orm.type.classreading.MetadataReaderFactory)
         */
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                throws IOException {

            return metadataReader.getClassMetadata().isInterface() && super.match(metadataReader, metadataReaderFactory);
        }
    }

    /**
     * Helper class to create a {@link TypeFilter} that matches if all the delegates match.
     *
     * @author Oliver Gierke
     */
    private static class AllTypeFilter implements TypeFilter {

        private final List <TypeFilter> delegates;

        /**
         * Creates a new {@link CustomRepositoryComponentProvider.AllTypeFilter} to match if all the given delegates match.
         *
         * @param delegates must not be {@literal null}.
         */
        public AllTypeFilter(List <TypeFilter> delegates) {

            Assert.notNull(delegates, "TypeFilter deleages must not be null!");
            this.delegates = delegates;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.orm.type.filter.TypeFilter#match(org.springframework.orm.type.classreading.MetadataReader, org.springframework.orm.type.classreading.MetadataReaderFactory)
         */
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                throws IOException {

            for (TypeFilter filter : delegates) {
                if (!filter.match(metadataReader, metadataReaderFactory)) {
                    return false;
                }
            }

            return true;
        }
    }
}
