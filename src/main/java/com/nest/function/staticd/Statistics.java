package com.nest.function.staticd;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by wzp on 2018/5/24.
 */
@Document
@Data
@Entity("statistics")
public class Statistics {
    private String _id, object;
//    @Reference
    private TagNameGroup tagNameGroup;
}
