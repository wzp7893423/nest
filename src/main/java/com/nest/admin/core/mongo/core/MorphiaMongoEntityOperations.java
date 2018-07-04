package com.nest.admin.core.mongo.core;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.nest.admin.core.mongo.config.MongoDataProperties;
import com.nest.function.staticd.Statistics;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wzp on 2018/6/28.
 */
public class MorphiaMongoEntityOperations implements MongoEntityOperations {

    private static final Logger logger = LoggerFactory.getLogger(MorphiaMongoEntityOperations.class);

    private final Morphia morphia = new Morphia();

    private ConcurrentHashMap <String, Datastore> datastores = new ConcurrentHashMap <String, Datastore>();

    private static final String DEFAULT_DATASTORE_NAME = "default";

    public MorphiaMongoEntityOperations() {
        this(null);
    }

    public MorphiaMongoEntityOperations(MongoDataProperties mongoDataProperties) {
        if (Optional.ofNullable(mongoDataProperties).isPresent()) {
            addDatastore(mongoDataProperties);
        }
    }

    public synchronized void addDatastore(MongoDataProperties mongoDataProperties) {
        Assert.notNull(mongoDataProperties, "mongoData config message is not allowed null");
        List <MongoDataProperties> mongoDataPropertiesList = buildMongoDataProperties(mongoDataProperties);
        for (MongoDataProperties mongoproperties : mongoDataPropertiesList) {
            Datastore datastore = morphia.createDatastore(createMongoClient(mongoproperties), mongoproperties.getDatabase());
            if (mongoDataPropertiesList.size() == 1) {
                datastores.put(DEFAULT_DATASTORE_NAME, datastore);
            } else {
                datastores.put(mongoDataProperties.getDatabase(), datastore);
            }
        }
    }

    private List <MongoDataProperties> buildMongoDataProperties(MongoDataProperties mongoDataProperties) {
        if (mongoDataProperties.mongosHave()) {
            return mongoDataProperties.getMongos();
        } else {
            return Lists.newArrayList(mongoDataProperties);
        }
    }

    public Datastore getDatastore(String dataName) {
        if (datastores.containsKey(dataName)) {
            return datastores.get(dataName);
        } else {
            RuntimeException noDatastore = new NullPointerException("no Datasotroe named " + dataName);
            logger.info("threre %s", noDatastore);
            throw noDatastore;
        }
    }

    private MongoClient createMongoClient(MongoDataProperties mongoDataProperties) {
        return new MongoClient(mongoDataProperties.getHost());
    }


    @Override
    public <T> List <T> findAll(Class<T> domainType) {
        return (List <T>) this.getDatastore("DTXYJK").find(Statistics.class).filter("object=","DTXYJK:TN:WTG055").asList();
    }
}
