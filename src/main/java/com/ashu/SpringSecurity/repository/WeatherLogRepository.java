package com.ashu.SpringSecurity.repository;

import com.ashu.SpringSecurity.entity.WeatherLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLogRepository extends JpaRepository<WeatherLog,Long> {
}
