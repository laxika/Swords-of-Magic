package com.swords.model.repository;

import com.swords.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Card card) {
        mongoTemplate.insert(card);
    }

}
