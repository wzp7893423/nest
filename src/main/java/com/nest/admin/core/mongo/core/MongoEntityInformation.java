package com.nest.admin.core.mongo.core;

import com.nest.admin.core.annotation.DbName;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;

import java.util.Optional;

/**
 * Created by wzp on 2018/6/15.
 */
public class MongoEntityInformation {

    private RepositoryMetadata repositoryMetadata;

    private final Optional<DbName> dataBaseName;

    public MongoEntityInformation(RepositoryMetadata repositoryMetadata) {
        this.repositoryMetadata = repositoryMetadata;

        this.dataBaseName = resolveDataBaseName(repositoryMetadata.getRepositoryInterface());
    }
    private Optional <DbName> resolveDataBaseName(Class <?> repositoryInterface) {
        return Optional.ofNullable(repositoryInterface.getAnnotation(DbName.class));
    }

    public static MongoEntityInformation getMongoEntityInfomation(RepositoryMetadata repositoryMetadata, RepositoryComposition.RepositoryFragments repositoryFragmentsToUse) {
        return new MongoEntityInformation(repositoryMetadata);
    }

    public Class <?> getDomainType() {
        return this.repositoryMetadata.getDomainType();
    }

    public String getDataBaseName(){
       if(dataBaseName.isPresent()){
           return dataBaseName.get().value();
       }else{
           return "default";
       }
    }

}
