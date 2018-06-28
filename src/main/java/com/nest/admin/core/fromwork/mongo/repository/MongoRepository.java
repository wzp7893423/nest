package com.nest.admin.core.fromwork.mongo.repository;

import com.google.common.collect.Lists;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by wzp on 2018/6/4.
 */
@NoRepositoryBean
public interface MongoRepository<T> {

    default public List <T> findALl() {
        System.out.println("ssdfsdfsadfasd");
        return Lists.newArrayList();
    }

    ;

}
