package com.nest.function;

import com.nest.function.sys.user.User;
import com.nest.function.sys.user.UserInfoRepository;
import com.nest.function.sys.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzp on 2018/5/10.
 */
@RestController
@Transactional
public class Function {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;


    @RequestMapping("/test")
    public Iterable <User> get(Pageable pageable) {
//        userRepository.findAll();
//        System.out.println(userRepository.findAllByUserName("wzp"));
//        System.out.println(userRepository.findName("wzp"));
        Map<String,Object> params = new HashMap<String,Object>(1);
        params.put("userName","wzp");
        System.out.println(userRepository.selectList("findUserEntry",params));
        List <User> list = Arrays.asList(new User[5]);
        return  userRepository.findAll();
    }
}
