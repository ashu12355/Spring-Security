package com.ashu.SpringSecurity.controller;

import com.ashu.SpringSecurity.entity.WeatherLog;
import com.ashu.SpringSecurity.service.WeatherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/logs")
public class WeatherLogController {

    @Autowired
    private WeatherLogService weatherLogService;

    @GetMapping("/{id}")
    public WeatherLog getLog(@PathVariable Long id){
     return weatherLogService.getLogById(id);
    }
    @PostMapping("/create")
    public WeatherLog createLog(@RequestBody WeatherLog weatherLog, Principal principal){
        weatherLog.setPerformedBy(principal.getName());
        weatherLog.setTimestamp(LocalDateTime.now());
        return weatherLogService.createLog(weatherLog);
    }
}
