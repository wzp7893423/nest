package com.nest.admin.core.fromwork.jdbc.mapper;

import com.nest.admin.core.fromwork.annotation.RowMapperClass;
import org.springframework.data.jdbc.repository.RowMapperMap;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * 查询返回结果映射工具配置
 * Created by wzp on 2018/5/17.
 */
public class CustomEntityRowMapperMap<T> implements RowMapperMap {

    @Override
    public <T> RowMapper <? extends T> rowMapperFor(Class <T> type) {
        Optional<RowMapperClass> rowMapperClass = Optional.ofNullable(type.getAnnotation(RowMapperClass.class));
        if(rowMapperClass.isPresent()){
            try {
                return rowMapperClass.get().mapperClass().getConstructor(type).newInstance(type);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
