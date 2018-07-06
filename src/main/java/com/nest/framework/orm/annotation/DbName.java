package com.nest.framework.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by wzp on 2018/7/5.
 */
@Documented
@Target({ElementType.TYPE})
public @interface DbName {
    String value();
}
