package com.nest.admin.core.fromwork.mongo.core;

import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;

/**
 * Created by wzp on 2018/6/15.
 */
public class MongoEntityInformation {

    private RepositoryMetadata repositoryMetadata;

    public MongoEntityInformation(RepositoryMetadata repositoryMetadata) {
        this.repositoryMetadata = repositoryMetadata;
    }

    public static MongoEntityInformation getMongoEntityInfomation(RepositoryMetadata repositoryMetadata, RepositoryComposition.RepositoryFragments repositoryFragmentsToUse) {
        return new MongoEntityInformation(repositoryMetadata);
    }

    public Class <?> getDomainType() {
        return this.repositoryMetadata.getDomainType();
    }
}
