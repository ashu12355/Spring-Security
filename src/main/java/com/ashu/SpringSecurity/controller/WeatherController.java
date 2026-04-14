package com.ashu.SpringSecurity.controller;

import com.ashu.SpringSecurity.entity.Weather;
import com.ashu.SpringSecurity.repository.WeatherRepository;
import com.ashu.SpringSecurity.service.CacheInspectionService;
import com.ashu.SpringSecurity.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    CacheInspectionService cacheInspectionService;

    @GetMapping
    @PreAuthorize("hasAuthority('WEATHER_READ')")
    public String getWeather(@RequestParam String city) {
        String weatherByCity
                = weatherService.getWeatherByCity(city);
        return weatherByCity;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherRepository.save(weather);
    }
    @PreAuthorize("hasAllRoles('ADMIN','USER')")
    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cacheData")
    public void getCacheDate() {
        cacheInspectionService.printCacheContents("weather");
    }
    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestParam String weatherUpdate) {
        return weatherService.updateWeather(city, weatherUpdate);
    }
    @DeleteMapping("/{city}")
    public String deleteWeather(@PathVariable String city) {
        weatherService.deleteWeather(city);
        return "Weather data for " + city + " has been deleted and cache evicted.";
    }
    @GetMapping("/health")
    public String healthWeather(){
        return "Healthy";
    }

}

