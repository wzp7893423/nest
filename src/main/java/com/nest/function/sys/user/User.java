package com.nest.function.sys.user;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.repository.query.Query;

/**
 * Created by wzp on 2018/5/14.
 */
@Data
@Alias("com.nest.admin.function.User")
public class User {
    private @Id Long id;

    private String userName,loginName;

    private Integer age;

    public User(Long id, String userName, String loginName, Integer age) {
        this.id = id;
        this.userName = userName;
        this.loginName = loginName;
        this.age = age;
    }


    public User(String userName, String loginName, Integer age) {
        this.userName = userName;
        this.loginName = loginName;
        this.age = age;
    }

    public User(){

    }
}
