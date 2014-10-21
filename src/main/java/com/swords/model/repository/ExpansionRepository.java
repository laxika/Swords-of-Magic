package com.swords.model.repository;

import com.swords.model.Expansion;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpansionRepository extends MongoRepository<Expansion, String> {

}
