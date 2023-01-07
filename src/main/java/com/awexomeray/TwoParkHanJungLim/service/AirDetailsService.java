package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dto.airDetails.DayAirAvg;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirDetailsService {

    private final AirDataDao airDataDao;

    private final MongoTemplate mongoTemplate;

    public DayAirAvg getAvgDayAir(String collectionName, Date date, String id) {

        SimpleDateFormat formatType = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatType.format(date);

        List<AirDataEntity> dayList = airDataDao.findAirDataByDay(collectionName, day, id);
        if (dayList.size() == 0) throw new ApiCustomException(ErrorCodes.NO_DATA_COLLECTION);
        double temp = 0, humi = 0, co2 = 0, tvoc = 0, pm01 = 0, pm25 = 0, pm10 = 0;
        for (AirDataEntity airData : dayList) {
            if (airData.getTemp() != null) temp += airData.getTemp();
            if (airData.getHumi() != null) humi += airData.getHumi();
            if (airData.getCo2() != null) co2 += airData.getCo2();
            if (airData.getTvoc() != null) tvoc += airData.getTvoc();
            if (airData.getPm01() != null) pm01 += airData.getPm01();
            if (airData.getPm25() != null) pm25 += airData.getPm25();
            if (airData.getPm10() != null) pm10 += airData.getPm10();
        }
        int size = dayList.size();

        DayAirAvg dayAirAvg = DayAirAvg.builder()
                .temp(temp / size)
                .humi(humi / size)
                .co2(co2 / size)
                .tvoc(tvoc / size)
                .pm01(pm01 / size)
                .pm25(pm25 / size)
                .pm10(pm10 / size)
                .build();

        return dayAirAvg;
    }

    public List<AirDataEntity> getAvgWeekAir(String collectionName, Date date, String id) {
        SimpleDateFormat formatType = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatType.format(date);
    }
}
