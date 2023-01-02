package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dto.mainDto.AirDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.GraphDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import com.awexomeray.TwoParkHanJungLim.model.AirDataModel;
import com.awexomeray.TwoParkHanJungLim.model.SensorModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final MongoTemplate mongoTemplate;

    public List<AirDataModel> getAirDataOfGraph(GraphDataDto graphDataDto) {

        //넘겨준 컬랙션이 유용한 정보인지 확인한다.
        findCollection(graphDataDto.getCollection());
        //넘겨준 센서의 정보가 저장되어 있는지 확인한다.
        findSensorData(graphDataDto.getSensors());
//        //해당 날짜의 센서별 공기질 데이터를 가져옴
//        List<AirDataModel> airDataModelList = getAirDataModelListAtDateTime(collectionName, latestRecordDateTime);
//
//        //필요 데이터 가공
//        List<SensorInfoDto> sensorInfoDtoList = makeSensorInfoDtoList(airDataModelList);

        return null;
    }

//    //필요 데이터 가공
//    private List<SensorInfoDto> makeSensorInfoDtoList(List<AirDataModel> airDataModelList) {
//        List<SensorInfoDto> sensorInfoDtoList = new ArrayList<>();
//
//        for (var airDataModel : airDataModelList) {
//
//            //공기질 데이터 추출
//            AirDataDto airDataDto = extractAirData(airDataModel);
//
//            String sensorName;
//            try {
//                //센서이름을 찾음
//                sensorName = findSensorName(airDataModel.getS_id());
//            } catch (NullPointerException e) {//센서이름이 존재하지 않는경우 예외처리
//                throw new ApiCustomException(ErrorCodes.NO_SEARCH_SENSOR_ID);
//            }
//
//            //센서정보 종합(센서이름 + 공기질데이터)
//            SensorInfoDto sensorInfoDto = SensorInfoDto.builder()
//                    .sensorName(sensorName)
//                    .logTime(airDataModel.getLogtime())
//                    .airData(airDataDto).build();
//
//            //센서정보 리스트에 추가
//            sensorInfoDtoList.add(sensorInfoDto);
//        }
//        return sensorInfoDtoList;
//    }
//
//    //센서이름을 찾음
//    private String findSensorName(String sensorId) throws NullPointerException {
//        String sensorCollectionName = "axr-sensor";
//
//        SensorModel sensorModel = mongoTemplate.findOne(
//                Query.query(Criteria.where("s_id").is(sensorId)),
//                SensorModel.class,
//                sensorCollectionName
//        );
//
//        return sensorModel.getName();
//    }
//
//    //공기질 데이터 추출
//    private AirDataDto extractAirData(AirDataModel airDataModel) {
//        return AirDataDto.builder()
//                .temp(airDataModel.getTemp())
//                .humi(airDataModel.getHumi())
//                .co2(airDataModel.getCo2())
//                .tvoc(airDataModel.getTvoc())
//                .pm01(airDataModel.getPm01())
//                .pm25(airDataModel.getPm25())
//                .pm10(airDataModel.getPm10()).build();
//    }
//
    //센서별 데이터 정보 있는지 확인
    private List findSensorData(List<String> sensors) {
        GraphDataDto graphDataDto;
        for (String element : sensors) {
            graphDataDto.getSensors().add(mongoTemplate.find(
                    Query.query(Criteria.where("s_id").is(element)),
                    AirDataModel.class,
                    "axr-sensor"
            ));
        }

    }

    //컬랙션 존재여부 확인
    private void findCollection(String collectionName){
        Set<String> collectionList = mongoTemplate.getCollectionNames();
        boolean isCollection = collectionList.contains(collectionName);
        //존재하지 않은 컬랙션일때
        if(!isCollection) {
            throw new ApiCustomException(ErrorCodes.NO_SEARCH_COLLECTION);
        }
        //공기질을 저장하는 컬랙션은 logtime이 기록되어 있다.
        AirDataModel isAirData = mongoTemplate.findOne(
                new Query().with(Sort.by(Sort.Direction.DESC, "logtime")).limit(1),
                AirDataModel.class,
                collectionName
        );
        //존재하는 컬랙션이지만 공기질 데이터를 저장하는지 확인한다.
        if(isAirData.getLogtime() == null) {
            throw new ApiCustomException(ErrorCodes.NO_DATA_COLLECTION);
        }
    }
}
