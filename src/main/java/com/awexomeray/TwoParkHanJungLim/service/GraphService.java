package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dao.SensorDao;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.GraphDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
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
    private final SensorDao sensorDao;
    private final MongoTemplate mongoTemplate;

    public List<SensorInfoDto> getAirDataOfGraph(GraphDataDto graphDataDto) {

        //넘겨준 정보가 저장되어 있는지 확인한다.
        getCollection(graphDataDto.getCollection());
        //넘겨준 센서의 이름으로 s_id를 찾는다.
        List<String> sersorName = getSensorName(graphDataDto.getSensors());
        //넘겨준 센서의 정보를 리스트에 담는다.
        getSensorData(graphDataDto.getCollection(), sersorName, graphDataDto.getLogTime());
//        //해당 날짜의 센서별 공기질 데이터를 가져옴
//        List<AirDataModel> airDataModelList = getAirDataModelListAtDateTime(collectionName, latestRecordDateTime);
//
//        //필요 데이터 가공
//        List<SensorInfoDto> sensorInfoDtoList = makeSensorInfoDtoList(airDataModelList);

        return null;
    }

    //센서이름에서 id 추출
    private List<String> getSensorName(List<String> sensorName) {
        List<SensorEntity> sensors = new ArrayList<>();
        sensors = sensorDao.findBySensorName(sensorName);

        return sensors;
    }

    //센서별 데이터 정보 있는지 확인
    private List<List<AirDataEntity>> getSensorData(String collectionName, List<String> sensors, String logTime) {
        List<List<AirDataEntity>> sensorData = new ArrayList<>();
        for (String element : sensors) {
            sensorData.add(airDataDao.findSensorData(collectionName, element, logTime));
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
