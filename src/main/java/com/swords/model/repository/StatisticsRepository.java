package com.swords.model.repository;

import com.swords.model.Statistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticsRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Statistics> findByBetweenDate(long startDate, long endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").lte(endDate).gte(startDate));

        return mongoTemplate.find(query, Statistics.class);
    }

    public Statistics findToday() {
        DateTime today = new DateTime().withTimeAtStartOfDay();

        Statistics todayStatistics = mongoTemplate.findById(today.getMillis(), Statistics.class);

        return todayStatistics == null ? new Statistics(today.getMillis()) : todayStatistics;
    }

    public void save(Statistics statistics) {
        mongoTemplate.save(statistics);
    }

}