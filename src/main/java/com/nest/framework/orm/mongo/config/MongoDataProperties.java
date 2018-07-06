package com.nest.framework.orm.mongo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzp on 2018/5/19.
 */
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Getter
@Setter
public class MongoDataProperties {

    private String database;

    private String host;

    private String port;

    private int connectTimeout;

    private int socketTimeout;

    private boolean socketKeepAlive;

    private List <MongoDataProperties> mongos = new ArrayList <MongoDataProperties>();

    public boolean mongosHave() {
        return mongos.size() > 0;
    }
}
