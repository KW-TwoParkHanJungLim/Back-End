package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dao.SensorDao;
import com.awexomeray.TwoParkHanJungLim.dto.graphDto.RequestGraphDataDto;
import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final AirDataDao airDataDao;
    private final SensorDao sensorDao;
    private final MongoTemplate mongoTemplate;

    public List<List<Map>> getAirDataOfGraph(RequestGraphDataDto requestGraphDataDto) {
        //넘겨준 컬렉션이 유효한지 확인한다.
        Boolean collection = isCollection(requestGraphDataDto.getCollection());

        //존재하지 않은 컬랙션일때
        if (!collection) throw new ApiCustomException(ErrorCodes.NO_SEARCH_COLLECTION);

        //공기질 데이터를 제대로 넘겨줬는지 확인
        Boolean airData = isAirData(requestGraphDataDto.getAirData());

        //존재하지 않은 공기질 데이터일 때
        if (!airData) throw new ApiCustomException(ErrorCodes.NO_SEARCH_AIR_TYPE);

        //넘겨준 센서의 정보를 리스트에 담는다.
        return getSensorData(requestGraphDataDto);
    }

    //센서 정보를 리스트에 담아온다.
    private List<List<Map>> getSensorData(RequestGraphDataDto requestGraphDataDto) {
        List<List<Map>> sensorData = new ArrayList<>();
        for (String sensorId : requestGraphDataDto.getSensors()) { //센서별 반복
            Boolean sensor = isSensor(sensorId);
            if (!sensor) throw new ApiCustomException(ErrorCodes.NO_SEARCH_SENSOR_ID);

            List<Map> allAirDataList = airDataDao.findSensorData(requestGraphDataDto, sensorId);
            List<Map> parseDataMapList = refineAllAirDataList(requestGraphDataDto, allAirDataList);
            sensorData.add(parseDataMapList);
        }
        return sensorData;
    }

    //10분단위로 정제한 데이터 리스트 추출
    private List<Map> refineAllAirDataList(RequestGraphDataDto requestGraphDataDto, List<Map> allAirDataList) {
        List<Map> newMapList = new ArrayList<>();
        Map avg = new HashMap<>();
        double sum = 0.0;
        LocalDateTime standardLogTime = LocalDateTime.parse(allAirDataList.get(0).get("logtime").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        //10분 단위로 자르기 위해 1의 자리 수를 0으로 초기화 ex)59분 -> 50분 / 09분 -> 00분
        standardLogTime = LocalDateTime.of(standardLogTime.getYear(),
                standardLogTime.getMonth(),
                standardLogTime.getDayOfMonth(),
                standardLogTime.getHour(),
                standardLogTime.getMinute() / 10 * 10,
                standardLogTime.getSecond());

        //10초 기준으로 저장되어 있는 전체 공기질 데이터 속에서 10분단위로 추려 새로운 리스트에 할당
        for (Map log : allAirDataList) {
            LocalDateTime currentDateTime = LocalDateTime.parse(log.get("logtime").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

            //리스트에 있는 데이터의 시간이 기준시 보다 크거나 같은 경우 새 리스트에 할당
            if (currentDateTime.isBefore(standardLogTime)) continue;
            currentDateTime = currentDateTime.withSecond(0);
            log.put("logtime", currentDateTime.toString() + ":00.000Z");
            log.put("value", log.get(requestGraphDataDto.getAirData()));
            log.remove(requestGraphDataDto.getAirData());
            newMapList.add(log);
            standardLogTime = standardLogTime.plusMinutes(10);

            //평균 구하기
            try {
                sum += (double) log.get("value");
            } catch (ClassCastException | NullPointerException exception){}
        }
        avg.put("s_id", allAirDataList.get(0).get("s_id"));
        avg.put("avg", sum / allAirDataList.size());

        newMapList.add(avg);
        return newMapList;
    }

    //컬랙션 존재여부 확인
    private Boolean isCollection(String collectionName) {
        Set<String> collectionList = mongoTemplate.getCollectionNames();
        return collectionList.contains(collectionName);
    }

    //공기질 데이터 제대로 넘겨줬는지 확인
    private Boolean isAirData(String airData) {
        switch (airData) {
            case "temp":
                break;
            case "humi":
                break;
            case "co2":
                break;
            case "tvoc":
                break;
            case "pm01":
                break;
            case "pm25":
                break;
            case "pm10":
                break;
            default:
                return false;
        }
        return true;
    }

    //컬랙션 존재여부 확인
    private Boolean isSensor(String sensorId) {
        String sensorCollectionName = "axr-sensor";
        SensorEntity sensorEntity = sensorDao.findBySensorId(sensorCollectionName, sensorId);
        return sensorEntity != null;
    }
}
