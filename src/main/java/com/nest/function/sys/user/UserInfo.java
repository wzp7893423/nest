package com.nest.function.sys.user;

import com.nest.admin.core.fromwork.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by wzp on 2018/5/15.
 */
@TableName("User")
@Data
public class UserInfo {
    private @Id Long id;

    private String userName,loginName;

    private Integer age;

    public UserInfo(Long id, String userName, String loginName, Integer age) {
        this.id = id;
        this.userName = userName;
        this.loginName = loginName;
        this.age = age;
    }

    public UserInfo() {
    }
}
