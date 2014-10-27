package com.swords.model.repository;

import com.swords.model.Expansion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ExpansionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Expansion expansion) {
        mongoTemplate.insert(expansion);
    }
    
    public Expansion findById(String id) {
        return mongoTemplate.findById(id, Expansion.class);
    }
    
    public List<Expansion> findAll() {
        return mongoTemplate.findAll(Expansion.class);
    }
    
    public List<Expansion> findAll(Sort sort) {
        Query query = new Query();
        query.with(sort);
        
        return mongoTemplate.find(query, Expansion.class);
    }
}
