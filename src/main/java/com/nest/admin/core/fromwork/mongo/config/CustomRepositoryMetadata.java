package com.nest.admin.core.fromwork.mongo.config;

import com.nest.admin.core.fromwork.mongo.MongoRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultCrudMethods;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.TypeInformation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by wzp on 2018/6/12.
 */
public class CustomRepositoryMetadata implements RepositoryMetadata {

    private final TypeInformation<?> typeInformation;
    private final Class<?> repositoryInterface;
    private final Lazy<CrudMethods> crudMethods;
    private final Class<?> domainType;

    public CustomRepositoryMetadata(Class<?> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        this.typeInformation = ClassTypeInformation.from(repositoryInterface);
        this.crudMethods = Lazy.of(() -> new DefaultCrudMethods(this));

        List<TypeInformation<?>> arguments = ClassTypeInformation.from(repositoryInterface) //
                .getRequiredSuperTypeInformation(MongoRepository.class)//
                .getTypeArguments();
        this.domainType = resolveTypeParameter(arguments,0,() -> String.format("Could not resolve domain type of %s!", repositoryInterface));
    }

    @Override
    public Class <?> getIdType() {
        return null;
    }

    @Override
    public Class <?> getDomainType() {
        return null;
    }

    @Override
    public Class <?> getRepositoryInterface() {
        return repositoryInterface;
    }

    @Override
    public Class <?> getReturnedDomainClass(Method method) {
        return null;
    }

    @Override
    public CrudMethods getCrudMethods() {
        return crudMethods.get();
    }

    @Override
    public boolean isPagingRepository() {
        return false;
    }

    @Override
    public Set<Class <?>> getAlternativeDomainTypes() {
        return null;
    }

    @Override
    public boolean isReactiveRepository() {
        return false;
    }

    public static RepositoryMetadata getMetadata(Class<?> repositoryInterface) {
        return  new CustomRepositoryMetadata(repositoryInterface);
    }
    private static Class<?> resolveTypeParameter(List<TypeInformation<?>> arguments, int index,
                                                 Supplier<String> exceptionMessage) {
        if (arguments.size() <= index || arguments.get(index) == null) {
            throw new IllegalArgumentException(exceptionMessage.get());
        }
        return arguments.get(index).getType();
    }
}
