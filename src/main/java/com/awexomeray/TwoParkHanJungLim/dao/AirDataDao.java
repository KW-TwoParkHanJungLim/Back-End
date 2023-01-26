package com.awexomeray.TwoParkHanJungLim.dao;

import com.awexomeray.TwoParkHanJungLim.dto.graphDto.RequestGraphDataDto;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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

        //해당 날짜의 데이터가 존재하지 않을 때 예외처리
        if (logtime.size() == 0) {
            throw new ApiCustomException(ErrorCodes.NO_SEARCH_DATE);
        }

        return logtime;
    }

    public List<AirDataEntity> findAirDataByDate(String collectionName, String beginDate, String endDate, String id) {
        return mongoTemplate.find(
                Query.query(Criteria.where("day").gte(beginDate).lt(endDate).and("s_id").is(id)),
                AirDataEntity.class,
                collectionName);
    }
}
