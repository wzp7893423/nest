package com.nest.function.staticd;

import com.nest.admin.core.fromwork.mongo.repository.MongoRepository;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by wzp on 2018/5/24.
 */
@Document
@Data
public class Statistics implements MongoRepository {
    private String id, object;

    private TagNameGroup tagNameGroup;
}
