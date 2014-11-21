package com.swords.model.repository;

import com.swords.model.CardCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CardCollectionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public CardCollection findById(String id) {
        return mongoTemplate.findById(id, CardCollection.class);
    }
    
    public CardCollection findByIdOrCreateIfNotExists(String id) {
        CardCollection collection = this.findById(id);
        
        return collection != null ? collection : new CardCollection(id);
    }
    
    public void save(CardCollection collection) {
        mongoTemplate.save(collection);
    }
    
    public void insertOrUpdateIfExists(Query query, Update update) {
        mongoTemplate.upsert(query, update, CardCollection.class);
    }
}
