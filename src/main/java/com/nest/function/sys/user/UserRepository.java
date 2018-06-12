package com.nest.function.sys.user;

import com.nest.admin.core.fromwork.jdbc.mybatis.MybatisRepository;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.List;

/**
 * Created by wzp on 2018/5/14.
 */
public interface UserRepository extends MybatisRepository<User,Long> {

    @Query("select id,userName,loginName,age from User where userName = :userName")
    List<User> getUserInfosByUserName(String userName);

    @Query("select id as user_id,userName as user_userName,userName,loginName as user_loginName from User where userName = :userName")
    List<UserEntry> findAllByUserName(String userName);

 }
