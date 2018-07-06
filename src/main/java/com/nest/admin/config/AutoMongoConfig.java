package com.nest.admin.config;

import com.nest.admin.core.mongo.config.EnableMongoRepository;
import com.nest.admin.core.mongo.config.MongoDataProperties;
import com.nest.admin.core.mongo.core.MongoEntityOperations;
import com.nest.admin.core.mongo.core.MorphiaMongoEntityOperations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wzp on 2018/6/5.
 */
@Configuration
@EnableMongoRepository("com.nest.function")
@EnableConfigurationProperties(MongoDataProperties.class)
public class AutoMongoConfig {

//    @Bean
//    public MongoEntityOperations initMongoEntityOperations(MongoDataProperties mongoDataProperties) {
//        return new MorphiaMongoEntityOperations(mongoDataProperties);
//    }
}
