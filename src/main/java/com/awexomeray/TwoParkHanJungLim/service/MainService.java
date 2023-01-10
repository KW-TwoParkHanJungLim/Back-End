package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dao.SensorDao;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.AirDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.entity.SensorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final AirDataDao airDataDao;
    private final SensorDao sensorDao;

    public List<SensorInfoDto> getAirDataOfSensors(String collectionName) {

        //가장 최근에 기록된 데이터의 로그시간을 찾음
        String latestRecordDateTime;
        try {
            latestRecordDateTime = getLatestRecordDateTime(collectionName);
            //공기질 데이터를 저장하는 Collection이 아닌 경우 예외처리
            if (latestRecordDateTime == null) throw new ApiCustomException(ErrorCodes.NO_DATA_COLLECTION);
        } catch (NullPointerException e) {
            throw new ApiCustomException(ErrorCodes.NO_SEARCH_COLLECTION);
        }

        //해당 날짜의 센서별 공기질 데이터를 가져옴
        List<AirDataEntity> airDataEntityList = getAirDataEntityListAtDateTime(collectionName, latestRecordDateTime);

        //필요 데이터 가공
        return makeSensorInfoDtoList(airDataEntityList);
    }

    //필요 데이터 가공
    private List<SensorInfoDto> makeSensorInfoDtoList(List<AirDataEntity> airDataEntityList) {
        List<SensorInfoDto> sensorInfoDtoList = new ArrayList<>();

        for (var airDataModel : airDataEntityList) {

            //공기질 데이터 추출
            AirDataDto airDataDto = extractAirData(airDataModel);

            String sensorName;
            try {
                //센서이름을 찾음
                sensorName = findSensorName(airDataModel.getS_id());
            } catch (NullPointerException e) {//센서이름이 존재하지 않는경우 예외처리
                throw new ApiCustomException(ErrorCodes.NO_SEARCH_SENSOR_ID);
            }

            //센서정보 종합(센서이름 + 공기질데이터)
            SensorInfoDto sensorInfoDto = SensorInfoDto.builder()
                    .sensorName(sensorName)
                    .sensorId(airDataModel.getS_id())
                    .logTime(airDataModel.getLogtime())
                    .airData(airDataDto).build();

            //센서정보 리스트에 추가
            sensorInfoDtoList.add(sensorInfoDto);
        }
        return sensorInfoDtoList;
    }

    //센서이름을 찾음
    private String findSensorName(String sensorId) throws NullPointerException {
        String sensorCollectionName = "axr-sensor";
        SensorEntity sensorEntity = sensorDao.findBySensorId(sensorCollectionName, sensorId);

        return sensorEntity.getName();
    }

    //공기질 데이터 추출
    private AirDataDto extractAirData(AirDataEntity airDataEntity) {
        return AirDataDto.builder()
                .temp(airDataEntity.getTemp())
                .humi(airDataEntity.getHumi())
                .co2(airDataEntity.getCo2())
                .tvoc(airDataEntity.getTvoc())
                .pm01(airDataEntity.getPm01())
                .pm25(airDataEntity.getPm25())
                .pm10(airDataEntity.getPm10()).build();
    }

    //해당 날짜의 센서별 공기질 데이터를 가져옴
    private List<AirDataEntity> getAirDataEntityListAtDateTime(String collectionName, String latestRecordDateTime) {
        return airDataDao.findByLogtime(collectionName, latestRecordDateTime);
    }

    //가장 최근에 기록된 데이터의 로그시간을 찾음
    private String getLatestRecordDateTime(String collectionName) throws NullPointerException {
        AirDataEntity latestRecordAirDataEntity = airDataDao.findLatestRecordAirData(collectionName);
        return latestRecordAirDataEntity.getLogtime();
    }

}
