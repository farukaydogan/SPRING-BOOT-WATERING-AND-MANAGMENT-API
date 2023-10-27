package com.humidty.arge.repository;

import com.humidty.arge.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {
    List<Device> findByUserId(Integer userId);
    Optional<Device> findBydeviceIDAndUserId(Integer deviceID, Integer userId);
    List<Device> findByLastUpdateTimeAfter(Date lastUpdateTime);
    List<Device> findByLastUpdateTimeBefore(Date lastUpdateTime);
}
