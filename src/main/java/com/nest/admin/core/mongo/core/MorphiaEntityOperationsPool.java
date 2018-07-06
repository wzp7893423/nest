package com.nest.admin.core.mongo.core;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.nest.admin.core.mongo.config.MongoDataProperties;
import com.nest.admin.util.Assert;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wzp on 2018/7/6.
 */
public class MorphiaEntityOperationsPool implements EntityOperationsPool {

    private static final Logger logger = LoggerFactory.getLogger(MorphiaMongoEntityOperations.class);

    private final MongoDataProperties mongoDataProperties;

    private static final String DEFAULT_DATASTORE_NAME = "default";

    private final Morphia morphia = new Morphia();

    private ConcurrentHashMap<String, Datastore> datastores = new ConcurrentHashMap <String, Datastore>(1);

    public MorphiaEntityOperationsPool(MongoDataProperties mongoDataProperties) {
        Assert.notNull(mongoDataProperties, "mongoData properties message is not allowed null");
        this.mongoDataProperties = mongoDataProperties;
        addDatastore(mongoDataProperties);
    }

    /**
     * 初始化mongo多数据源
     * @param mongoDataProperties
     */
    public synchronized void addDatastore(MongoDataProperties mongoDataProperties) {
        List<MongoDataProperties> propertiesList = buildMongoDataProperties(mongoDataProperties);
        propertiesList.forEach((property)->{
             putDatastore(property.getDatabase(),createDatastore(property));
        });
    }

    /**
     * 处理配置文件
     * @param mongoDataProperties
     * @return
     */
    private List <MongoDataProperties> buildMongoDataProperties(MongoDataProperties mongoDataProperties) {
        if (mongoDataProperties.mongosHave()) {
            return mongoDataProperties.getMongos();
        } else {
            return Lists.newArrayList(mongoDataProperties);
        }
    }

    private void putDatastore(String dataBaseName,Datastore datastore){
        this.datastores.put(dataBaseName,datastore);
    }

    private Datastore createDatastore(MongoDataProperties property){
        return this.morphia.createDatastore(createMongoClient(property),
                Optional.ofNullable(property.getDatabase()).orElse(DEFAULT_DATASTORE_NAME));
    }

    public Datastore getDatastore(String dataName) {
        Datastore datastore = datastores.get(dataName);
        Assert.notNull(datastore,"no Datasotroe named " + dataName);
        return datastore;
    }

    private MongoClient createMongoClient(MongoDataProperties property) {
        return new MongoClient(mongoDataProperties.getHost());
    }

    @Override
    public MongoEntityOperations getMongoEntityOperation() {
        return getMongoEntityOperation(DEFAULT_DATASTORE_NAME);
    }

    @Override
    public MongoEntityOperations getMongoEntityOperation(String dataBaseName) {
        return new MorphiaMongoEntityOperations(getDatastore(dataBaseName));
    }
}
