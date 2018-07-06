package com.nest.framework.orm.mongo.support;

import com.nest.framework.orm.mongo.repository.MongoRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.query.DefaultEvaluationContextProvider;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.util.Lazy;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by wzp on 2018/6/12.
 */
public abstract class AbstractMongoRepositorySupport<T extends MongoRepository> implements InitializingBean, FactoryBean,
        BeanFactoryAware, BeanClassLoaderAware, ApplicationEventPublisherAware {

    private BeanFactory beanFactory;
    private ClassLoader classLoader;
    private Lazy <T> repository;
    private boolean lazyInit;
    private MongoRepositoryFactory factory;
    private final Class <? extends T> repositoryInterface;

    private QueryLookupStrategy.Key queryLookupStrategyKey;
    private Optional <Class <?>> repositoryBaseClass = Optional.empty();
    private Optional <Object> customImplementation = Optional.empty();
    private Optional <RepositoryComposition.RepositoryFragments> repositoryFragments = Optional.empty();
    private NamedQueries namedQueries;
    private EvaluationContextProvider evaluationContextProvider = DefaultEvaluationContextProvider.INSTANCE;
    private ApplicationEventPublisher publisher;

    public void setQueryLookupStrategyKey(QueryLookupStrategy.Key queryLookupStrategyKey) {
        this.queryLookupStrategyKey = queryLookupStrategyKey;
    }

    public void setRepositoryBaseClass(Optional <Class <?>> repositoryBaseClass) {
        this.repositoryBaseClass = repositoryBaseClass;
    }

    public void setCustomImplementation(Optional <Object> customImplementation) {
        this.customImplementation = customImplementation;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public void setRepositoryFragments(RepositoryComposition.RepositoryFragments repositoryFragments) {
        this.repositoryFragments = Optional.ofNullable(repositoryFragments);
    }

    public void setNamedQueries(NamedQueries namedQueries) {
        this.namedQueries = namedQueries;
    }

    public void setEvaluationContextProvider(EvaluationContextProvider evaluationContextProvider) {
        this.evaluationContextProvider = evaluationContextProvider;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    AbstractMongoRepositorySupport(Class <? extends T> repositoryInterface) {
        Assert.notNull(repositoryInterface, "repositoryInterface is null");
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        return repository.get();
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Class <?> getObjectType() {
        return repositoryInterface;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        this.factory = createRepositoryFactory();

        this.factory.setBeanClassLoader(classLoader);

        RepositoryComposition.RepositoryFragments customImplementationFragment = customImplementation //
                .map(RepositoryComposition.RepositoryFragments::just) //
                .orElseGet(RepositoryComposition.RepositoryFragments::empty);

        RepositoryComposition.RepositoryFragments repositoryFragmentsToUse = this.repositoryFragments //
                .orElseGet(RepositoryComposition.RepositoryFragments::empty) //
                .append(customImplementationFragment);

        this.repository = Lazy.of(() -> this.factory.getRepository(repositoryInterface, repositoryFragmentsToUse));

        if (!lazyInit) {
            repository.get();
        }
    }

    protected abstract MongoRepositoryFactory createRepositoryFactory();
}
