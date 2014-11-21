package com.swords.model.repository;

import com.swords.model.ExpansionCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExpansionCollectionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ExpansionCollection findById(String id) {
        return mongoTemplate.findById(id, ExpansionCollection.class);
    }

    public ExpansionCollection findByIdOrCreateIfNotExists(String id) {
        ExpansionCollection collection = this.findById(id);

        return collection != null ? collection : new ExpansionCollection(id);
    }

    public void save(ExpansionCollection collection) {
        mongoTemplate.save(collection);
    }

}
