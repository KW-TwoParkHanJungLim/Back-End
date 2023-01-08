package com.awexomeray.TwoParkHanJungLim.dao;

import com.awexomeray.TwoParkHanJungLim.dto.graphDto.RequestGraphDataDto;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Map> findSensorData(RequestGraphDataDto requestGraphDataDto, String sensorId) {

        Query query = new Query(new Criteria("s_id").is(sensorId));
        query.addCriteria(new Criteria("logtime").regex(requestGraphDataDto.getLogTime()));
        query.fields()
                .include("s_id")
                .include("logtime")
                .include(requestGraphDataDto.getAirData())
                .exclude("_id");

        List<Map> logtime = mongoTemplate.find(
                query,
                Map.class,
                requestGraphDataDto.getCollection()
        );

        return logtime;
    }
}
