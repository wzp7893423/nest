package com.nest.framework.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by wzp on 2018/7/6.
 */
public class Assert {

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(@Nonnull Object object, String message){
        if(object == null){
            throw new IllegalArgumentException(message);
        }
    }
}
