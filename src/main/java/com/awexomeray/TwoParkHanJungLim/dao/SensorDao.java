package com.awexomeray.TwoParkHanJungLim.dao;

import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class SensorDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public SensorEntity findBySensorId(String sensorCollectionName, String sensorId) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("s_id").is(sensorId)),
                SensorEntity.class,
                sensorCollectionName
        );
    }
}
