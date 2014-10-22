package com.swords.model.repository;

import com.swords.model.Expansion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExpansionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Expansion expansion) {
        mongoTemplate.insert(expansion);
    }
}
