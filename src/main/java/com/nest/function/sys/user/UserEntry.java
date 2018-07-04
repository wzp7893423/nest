package com.nest.function.sys.user;

import lombok.Data;

/**
 * Created by wzp on 2018/5/17.
 */
@Data
public class UserEntry {

    private String userName, loginName;

    private int age;

    public UserEntry() {

    }
}
