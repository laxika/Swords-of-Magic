package com.swords.model.repository;

import com.swords.model.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CollectionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Collection findById(String id) {
        return mongoTemplate.findById(id, Collection.class);
    }
    
    public void insertOrUpdateIfExists(Query query, Update update) {
        mongoTemplate.upsert(query, update, Collection.class);
    }
}
