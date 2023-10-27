package com.humidty.arge.repository;

import com.humidty.arge.model.SensorNutrient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface SensorNutrientRepository extends JpaRepository<SensorNutrient, Integer> {
//        Optional<Nutrient> findById(int id);

//    @Query("SELECT sn FROM SensorNutrient sn WHERE sn.sensor.device.deviceID = :deviceID AND sn.pH IS NOT NULL ORDER BY sn.createTime DESC")
//    SensorNutrient getLatestDeviceSensorNpkValues(@Param("deviceID") Integer deviceId);

    @Query("SELECT sn FROM SensorNutrient sn WHERE sn.sensor.device.deviceID = :deviceID AND sn.pH != 0 ORDER BY sn.createTime DESC LIMIT 1")
    Optional<SensorNutrient> findTopBySensor_Device_DeviceIDAndPhIsNotNullOrderByCreateTimeDesc(Integer deviceID);

    @Modifying
    @Transactional
    @Query("DELETE FROM SensorNutrient sn WHERE sn.createTime < :threshold")
    void deleteOldSensorNutrients(@Param("threshold") Date threshold);
}
