package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirDetailsService {

    private final AirDataDao airDataDao;

    private final MongoTemplate mongoTemplate;
    public List<AirDataEntity> getAvgDayAir(Date date, String id){
        return null;
    }

    public List<AirDataEntity> getAvgWeekAir(Date date, String id){
        return null;
    }
}
