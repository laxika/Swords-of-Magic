package com.swords.model.repository;

import com.swords.model.Card;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Card card) {
        mongoTemplate.insert(card);
    }
    
    public List<Card> findByExpansionId(String expansionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("expansion").is(expansionId));

        return mongoTemplate.find(query, Card.class);
    }
    
    public boolean exists(Query query) {
        return mongoTemplate.exists(query, Card.class);
    }

}
