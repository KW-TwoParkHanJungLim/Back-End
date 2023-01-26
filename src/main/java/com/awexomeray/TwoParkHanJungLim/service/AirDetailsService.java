package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.AirDataDao;
import com.awexomeray.TwoParkHanJungLim.dto.airDetails.AirAvgDto;
import com.awexomeray.TwoParkHanJungLim.entity.AirDataEntity;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirDetailsService {

    private final AirDataDao airDataDao;

    public AirAvgDto getAvgAir(String collectionName, String date, String id, int period) {
        LocalDate afterDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        String startDate = getDate(afterDate, 1 - period);
        String endDate = getDate(afterDate, 1);
        List<AirDataEntity> dayList = airDataDao.findAirDataByDate(collectionName, startDate, endDate, id);
        if (dayList.size() == 0) throw new ApiCustomException(ErrorCodes.NO_DATA_COLLECTION);
        Double temp = 0.0, humi = 0.0, co2 = 0.0, tvoc = 0.0, pm01 = 0.0, pm25 = 0.0, pm10 = 0.0;
        int tempSize = 0, humiSize = 0, co2Size = 0, tvocSize = 0, pm01Size = 0, pm25Size = 0, pm10Size = 0;
        for (AirDataEntity airData : dayList) {
            if (airData.getTemp() != null) {
                tempSize++;
                temp += airData.getTemp();
            }
            if (airData.getHumi() != null) {
                humiSize++;
                humi += airData.getHumi();
            }
            if (airData.getCo2() != null) {
                co2Size++;
                co2 += airData.getCo2();
            }
            if (airData.getTvoc() != null) {
                tvocSize++;
                tvoc += airData.getTvoc();
            }
            if (airData.getPm01() != null) {
                pm01Size++;
                pm01 += airData.getPm01();
            }
            if (airData.getPm25() != null) {
                pm25Size++;
                pm25 += airData.getPm25();
            }
            if (airData.getPm10() != null) {
                pm10Size++;
                pm10 += airData.getPm10();
            }
        }

        if (tempSize == 0) temp = null;
        else temp /= tempSize;

        if (humiSize == 0) humi = null;
        else humi /= humiSize;

        if (co2Size == 0) co2 = null;
        else co2 /= co2Size;

        if (tvocSize == 0) tvoc = null;
        else tvoc /= tvocSize;

        if (pm01Size == 0) pm01 = null;
        else pm01 /= pm01Size;

        if (pm10Size == 0) pm10 = null;
        else pm10 /= pm10Size;

        if (pm25Size == 0) pm25 = null;
        else pm25 /= pm25Size;

        AirAvgDto airAvgDto = AirAvgDto.builder()
                .temp(temp)
                .humi(humi)
                .co2(co2)
                .tvoc(tvoc)
                .pm01(pm01)
                .pm25(pm25)
                .pm10(pm10)
                .build();
        return airAvgDto;
    }


    private String getDate(LocalDate date, int addDay) {
        LocalDate modDate = date.plusDays(addDay);
        return modDate.toString();
    }
}
