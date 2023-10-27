package com.humidty.arge.repository;

import com.humidty.arge.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SensorRepository extends JpaRepository<Sensor, Integer> {

//        Page<sensor> findByDeviceId(Integer  id, Pageable pageable);
//        sensor findByDevice(Integer  id);
     Sensor findBySensorIdAndDevice_deviceID(Integer sensorSensorId, Integer deviceId);

}
