package com.nest.admin.core.mongo.config;

import com.nest.admin.core.mongo.support.MongoRepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by wzp on 2018/6/4.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MonogRepositoryRegistrar.class)
public @interface EnableMongoRepository {

    String[] value() default {};

    String[] basePackages() default {};

    Class[] basePackageClasses() default {};

    ComponentScan.Filter[] includeFilters() default {};

    ComponentScan.Filter[] excludeFilters() default {};

    Class <?> repositoryFactoryBeanClass() default MongoRepositoryFactoryBean.class;

    String namedQueriesLocation() default "";

    String repositoryImplementationPostfix() default "Impl";

}
