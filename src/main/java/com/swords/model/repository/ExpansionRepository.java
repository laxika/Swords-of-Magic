package com.swords.model.repository;

import com.swords.model.Expansion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ExpansionRepository {

    private static final Logger logger = LoggerFactory.getLogger(ExpansionRepository.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Expansion expansion) {
        logger.info("Inserting new expansion: " + expansion.getName());

        mongoTemplate.insert(expansion);
    }
}
