package com.nest.admin.core.fromwork.mongo.repository;

import com.google.common.collect.Lists;
import org.mongodb.morphia.converters.ObjectIdConverter;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Indexed;

import java.util.List;

/**
 * Created by wzp on 2018/6/4.
 */
@NoRepositoryBean
public interface MongoRepository<T> {

    default  public List<T> findALl(){
        System.out.println("ssdfsdfsadfasd");
        return Lists.newArrayList();
    };

}
