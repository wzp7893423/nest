package com.nest.admin.core.fromwork.jdbc.mybatis;

import org.springframework.data.jdbc.core.JdbcEntityOperations;

import java.util.List;
import java.util.Map;

/**
 * Created by wzp on 2018/5/18.
 */
public interface MybatisEntityOperations extends JdbcEntityOperations{

    <T> List<T> selectList(String namespance, Map<String,Object> params,Class<T> domainType);
}
