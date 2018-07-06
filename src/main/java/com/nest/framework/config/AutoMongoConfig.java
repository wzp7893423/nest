package com.nest.framework.config;

import com.nest.framework.orm.mongo.config.EnableMongoRepository;
import com.nest.framework.orm.mongo.config.MongoDataProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
