package com.swords.model.repository;

import com.swords.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {

}
