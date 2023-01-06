package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dto.graphDto.RequestGraphDataDto;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final AirDataDao airDataDao;
    private final MongoTemplate mongoTemplate;

    public List<Map> getAirDataOfGraph(RequestGraphDataDto requestGraphDataDto) {

        //넘겨준 컬렉션이 유효한지 확인한다.
        getCollection(requestGraphDataDto.getCollection());

        //넘겨준 센서의 정보를 리스트에 담는다.
        List<Map> airDataList = getSensorData(requestGraphDataDto);

        //데이터 가공
        List<Map> graphData = optimizeData(airDataList);
        return graphData;
    }

    //_id를 제외한 정보로 가공한다.
    private List<Map> optimizeData(List<Map> airDataList) {
        List<Map> mapList = new ArrayList<>();

        for (var airData : airDataList) {
            Map element = new HashMap<>();
            Iterator<?> keys = airData.keySet().iterator();

            for (var key : airData.keySet()) {
                if (key.equals("_id")) continue;
                element.put(key, airData.get(key));
            }
            mapList.add(element);
        }
        return mapList;
    }

    //센서 정보를 리스트에 담아온다.
    private List<Map> getSensorData(RequestGraphDataDto requestGraphDataDto) {
        List<Map> sensorData = new ArrayList<>();
        for (String element : requestGraphDataDto.getSensors()) {
            sensorData.add(airDataDao.findSensorData(requestGraphDataDto, element));
        }
        return sensorData;
    }

    //컬랙션 존재여부 확인
    private void getCollection(String collectionName) {
        Set<String> collectionList = mongoTemplate.getCollectionNames();
        boolean isCollection = collectionList.contains(collectionName);
        //존재하지 않은 컬랙션일때
        if (!isCollection) throw new ApiCustomException(ErrorCodes.NO_SEARCH_COLLECTION);
    }
}
