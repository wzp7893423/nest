package com.nest.function.sys.user;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;

import java.sql.Clob;
import java.util.Map;

/**
 * Created by wzp on 2018/5/17.
 */
@Data
public class UserEntry {
    private String userName,loginName;

    private int age;

    public UserEntry() {

    }
}
