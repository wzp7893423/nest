package com.nest.admin.core.fromwork.annotation;

import org.springframework.data.jdbc.core.EntityRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.lang.annotation.*;

/**
 * 指定查询返回结果映射工具注解
 * Created by wzp on 2018/5/18.
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RowMapperClass {

    enum MapperClass {
        DEFAULT(EntityRowMapper.class), SINGL_COLUMN(SingleColumnRowMapper.class),
        ENTITY(EntityRowMapper.class), BEAN_PROPERTY(BeanPropertyRowMapper.class),
        COLUMN_MAP(ColumnMapRowMapper.class);

        MapperClass(Class <? extends RowMapper> type) {
            this.type = type;
        }

        private Class <? extends RowMapper> type;

        Class <? extends RowMapper> type() {
            return this.type;
        }
    }

    Class <? extends RowMapper> mapperClass() default EntityRowMapper.class;
}
