package com.awexomeray.TwoParkHanJungLim.dao;

import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AirDataDao {
    @Autowired
    private MongoTemplate mongoTemplate;


    public AirDataEntity findLatestRecordAirData(String collectionName) {
        return mongoTemplate.findOne(
                new Query().with(Sort.by(Sort.Direction.DESC, "logtime")).limit(1),
                AirDataEntity.class,
                collectionName
        );
    }

    public List<AirDataEntity> findByLogtime(String collectionName, String latestRecordDateTime) {
        return mongoTemplate.find(
                Query.query(Criteria.where("logtime").is(latestRecordDateTime)),
                AirDataEntity.class,
                collectionName
        );
    }

    public List<AirDataEntity> findSensorData(String collectionName, String sensorId, String logTime) {
        Query query = new Query(new Criteria("s_id").all(sensorId));
        query.addCriteria(new Criteria("logtime").regex(logTime));
        return mongoTemplate.find(
                query,
                AirDataEntity.class,
                collectionName
        );
    }
}
