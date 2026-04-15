package com.ashu.SpringSecurity.service;

import com.ashu.SpringSecurity.entity.Weather;
import com.ashu.SpringSecurity.entity.WeatherLog;
import com.ashu.SpringSecurity.repository.WeatherLogRepository;
import com.ashu.SpringSecurity.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class WeatherLogService {

    @Autowired
    private WeatherLogRepository weatherLogRepository;

    @PostAuthorize("returnObject.performedBy == authentication.name")
    public WeatherLog getLogById(Long id){
        return weatherLogRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Log Not Found"));
    }
    public WeatherLog createLog(WeatherLog log){
        return  weatherLogRepository.save(log);
    }
}
