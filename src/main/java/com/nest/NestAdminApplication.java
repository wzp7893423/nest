package com.nest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by wzp on 2018/5/8.
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class NestAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(NestAdminApplication.class,args);
    }
}
