package com.swords.model.repository;

import com.swords.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CardRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Card card) {
        mongoTemplate.insert(card);
    }

}
