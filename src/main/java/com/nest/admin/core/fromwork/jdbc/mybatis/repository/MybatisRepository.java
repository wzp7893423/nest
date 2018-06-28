package com.nest.admin.core.fromwork.jdbc.mybatis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by wzp on 2018/5/17.
 */
public interface MybatisRepository<T, ID> extends PagingAndSortingRepository <T, ID> {

    List <T> selectList(String namespance, Map <String, Object> params);
}
