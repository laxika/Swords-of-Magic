package com.swords.model.repository;

import com.swords.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

class UserRepositoryImpl implements CustomUserRepository {

    private final MongoOperations operations;

    @Autowired
    public UserRepositoryImpl(MongoOperations operations) {
        Assert.notNull(operations, "MongoOperations must not be null!");
        
        this.operations = operations;
    }

    @Override
    public User queryByNameAndPass(String username, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username).and("password").is(password));

        return operations.findOne(query, User.class);
    }

}
