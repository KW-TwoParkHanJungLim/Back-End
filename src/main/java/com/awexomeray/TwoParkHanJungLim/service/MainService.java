package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dto.mainDto.AirDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomExcption;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import com.awexomeray.TwoParkHanJungLim.model.AirDataModel;
import com.awexomeray.TwoParkHanJungLim.model.SensorModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MongoTemplate mongoTemplate;

    public List<SensorInfoDto> getAirDataOfSensors(String collectionName) {

        //가장 최근에 기록된 데이터의 로그시간을 찾음
        String latestRecordDateTime = findLatestRecordDateTime(collectionName);

        //해당 날짜의 센서별 공기질 데이터를 가져옴
        List<AirDataModel> airDataModelList = getAirDataModelListAtDateTime(collectionName, latestRecordDateTime);

        //필요 데이터 가공
        List<SensorInfoDto> sensorInfoDtoList = makeSensorInfoDtoList(airDataModelList);

        return sensorInfoDtoList;
    }

    //필요 데이터 가공
    private List<SensorInfoDto> makeSensorInfoDtoList(List<AirDataModel> airDataModelList) {
        List<SensorInfoDto> sensorInfoDtoList = new ArrayList<>();

        for (var airDataModel : airDataModelList) {

            //공기질 데이터 추출
            AirDataDto airDataDto = extractAirData(airDataModel);

            String sensorName;
            try {
                //센서이름을 찾음
                sensorName = findSensorNameByS_id(airDataModel.getS_id());
            } catch (NullPointerException e) {//센서이름이 존재하지 않는경우 예외처리
                throw new ApiCustomExcption(ErrorCodes.NO_SEARCH_SENSOR_ID);
            }

            //센서정보 종합(센서이름 + 공기질데이터)
            SensorInfoDto sensorInfoDto = SensorInfoDto.builder()
                    .sensorName(sensorName)
                    .logtime(airDataModel.getLogtime())
                    .airData(airDataDto).build();

            //센서정보 리스트에 추가
            sensorInfoDtoList.add(sensorInfoDto);
        }
        return sensorInfoDtoList;
    }

    //센서이름을 찾음
    private String findSensorNameByS_id(String s_id) throws NullPointerException {
        String sensorCollectionName = "axr-sensor";

        SensorModel sensorModel = mongoTemplate.findOne(
                Query.query(Criteria.where("s_id").is(s_id)),
                SensorModel.class,
                sensorCollectionName
        );

        return sensorModel.getName();
    }

    //공기질 데이터 추출
    private AirDataDto extractAirData(AirDataModel airDataModel) {
        return AirDataDto.builder()
                .temp(airDataModel.getTemp())
                .humi(airDataModel.getHumi())
                .co2(airDataModel.getCo2())
                .tvoc(airDataModel.getTvoc())
                .pm01(airDataModel.getPm01())
                .pm25(airDataModel.getPm25())
                .pm10(airDataModel.getPm10()).build();
    }

    //해당 날짜의 센서별 공기질 데이터를 가져옴
    private List<AirDataModel> getAirDataModelListAtDateTime(String collectionName,String latestRecordDateTime) {
        return mongoTemplate.find(
                Query.query(Criteria.where("logtime").is(latestRecordDateTime)),
                AirDataModel.class,
                collectionName
        );
    }

    //가장 최근에 기록된 데이터의 로그시간을 찾음
    private String findLatestRecordDateTime(String collectionName) {
        AirDataModel lastRecordAirDataModel = mongoTemplate.findOne(
                new Query().with(Sort.by(Sort.Direction.DESC, "logtime")).limit(1),
                AirDataModel.class,
                collectionName
        );
        return lastRecordAirDataModel.getLogtime();
    }
}
