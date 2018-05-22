package com.nest.function.sys.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wzp on 2018/5/14.
 */
@Transactional
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {

    default UserInfo getUserInfoBy(Long id){
        return findById(id).orElse(new UserInfo(2L,"default_UserName","default_LoginName",29));
    }

    @Query("select userName,loginName,age from User where userName = :userName")
    List<UserInfo> getUserInfosByUserName(@Param("userName") String userName);
}
