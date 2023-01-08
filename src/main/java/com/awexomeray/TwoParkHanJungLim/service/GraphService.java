package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dto.graphDto.RequestGraphDataDto;
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
    private final MongoTemplate mongoTemplate;

    public List<List<Map>> getAirDataOfGraph(RequestGraphDataDto requestGraphDataDto) {
        //넘겨준 컬렉션이 유효한지 확인한다.
        getCollection(requestGraphDataDto.getCollection());

        //넘겨준 센서의 정보를 리스트에 담는다.
        List<List<Map>> airDataList = getSensorData(requestGraphDataDto);

        //데이터 가공
        return airDataList;
    }

    //_id를 제외한 정보로 가공한다.
    private List<List<Map>> optimizeData(List<List<Map>> airDataList) {
        for (var list : airDataList) {
            for (var airData : list) {
                airData.remove("_id");
            }
        }
        return airDataList;
    }

    //센서 정보를 리스트에 담아온다.
    private List<List<Map>> getSensorData(RequestGraphDataDto requestGraphDataDto) {
        List<List<Map>> sensorData = new ArrayList<>();
        for (String element : requestGraphDataDto.getSensors()) {
            List<Map> allAirDataList = airDataDao.findSensorData(requestGraphDataDto, element);
            List<Map> parseDataMapList = refineAllAirDataList(allAirDataList);
            sensorData.add(parseDataMapList);
        }
        return sensorData;
    }

    //10분단위로 정제한 데이터 리스트 추출
    private List<Map> refineAllAirDataList(List<Map> allAirDataList) {
        List<Map> newMapList = new ArrayList<>();
        //리스트의 첫 번째로 저장되어 있는 값의 logtime을 기준시로 잡아 String 시간을 LocalDateTime으로 형변환
        LocalDateTime logtime1 = LocalDateTime.parse(allAirDataList.get(0).get("logtime").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        //10분 단위로 자르기 위해 1의 자리 수를 0으로 초기화 ex)59분 -> 50분 / 09분 -> 00분
        logtime1 = LocalDateTime.of(logtime1.getYear(), logtime1.getMonth(), logtime1.getDayOfMonth(), logtime1.getHour(), logtime1.getMinute() / 10 * 10, logtime1.getSecond());
        //10초 기준으로 저장되어 있는 전체 공기질 데이터 속에서 10분단위로 추려 새로운 리스트에 할당
        for (Map log : allAirDataList) {
            LocalDateTime currentDateTime = LocalDateTime.parse(log.get("logtime").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
            //리스트에 있는 데이터의 시간이 기준시 보다 크거나 같은 경우 새 리스트에 할당
            if (currentDateTime.isBefore(logtime1)) continue;
            newMapList.add(log);
            logtime1 = logtime1.plusMinutes(10);
        }
        return newMapList;
    }

    //컬랙션 존재여부 확인
    private void getCollection(String collectionName) {
        Set<String> collectionList = mongoTemplate.getCollectionNames();
        boolean isCollection = collectionList.contains(collectionName);
        //존재하지 않은 컬랙션일때
        if (!isCollection) throw new ApiCustomException(ErrorCodes.NO_SEARCH_COLLECTION);
    }
}
