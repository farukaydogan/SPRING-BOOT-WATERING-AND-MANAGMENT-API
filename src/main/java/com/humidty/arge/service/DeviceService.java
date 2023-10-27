package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.model.Device;
import com.humidty.arge.model.Sensor;
import com.humidty.arge.repository.DeviceRepository;
import com.humidty.arge.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {

    private DeviceRepository deviceRepository;
    private UserService userService;
    private SensorRepository sensorRepository;
    private SessionManagementService sessionManagementService;


    public List<Device> getDevice() {
        return deviceRepository.findAll();
    }

    public List<Device> getDevicesByUserId(Integer userId) {
        return deviceRepository.findByUserId(userId);
    }


    public List<Device> getDevicesLastUpdatedAfter(Date date) {
        return deviceRepository.findByLastUpdateTimeAfter(date);
    }

    public List<Device> getDevicesLastUpdatedBefore(Date date) {
        return deviceRepository.findByLastUpdateTimeBefore(date);
    }

    public Device getDeviceById(Integer deviceID, Integer userId) {
        return deviceRepository.findBydeviceIDAndUserId(deviceID, userId).orElseThrow(() -> new RuntimeException("Device Not Found"));
    }

    public Device getDeviceByIdOnlyUsesMqtt(Integer deviceID, String userToken) {
        return userService.findByUserTokenAndDeviceId(userToken, deviceID);
    }

    public Device createDevice(Device device) {
        Device newDevice = new Device();
        newDevice.setDeviceID(device.getDeviceID());
        newDevice.setWateringPeriod(WateringPeriod.AWAIT_SATURATION);
        newDevice.setOffWatering(false);
        newDevice.setUser(device.getUser());
        return deviceRepository.save(newDevice);
    }

    public Sensor getSensorBySensorSensorId(Device device, Integer sensorSensorId) {
        Sensor sensor = sensorRepository.findBySensorIdAndDevice_deviceID(sensorSensorId, device.getDeviceID());

        if (sensor == null) {
            // Sensor bulunamadı, yeni bir sensor oluştur
            sensor = new Sensor();
            sensor.setSensorId(sensorSensorId);
            sensor.setDevice(device);

            // Yeni oluşturulan sensor'u kaydedin (Eğer veritabanına kaydetmek istiyorsanız)
            sensorRepository.save(sensor);
        }

        return sensor;
    }

    public void updateDevice(Device updateDevice) {
        Device oldDevice = getDeviceById(updateDevice.getDeviceID(), updateDevice.getUser().getId());

        if (updateDevice.getWateringPeriod() != null) {
            oldDevice.setWateringPeriod(updateDevice.getWateringPeriod());
        }

        if (updateDevice.getOffWatering() != null) {
            oldDevice.setOffWatering(updateDevice.getOffWatering());
        }

        if (updateDevice.getStopWateringHumidityThreshold() != 0) {
            oldDevice.setStopWateringHumidityThreshold(updateDevice.getStopWateringHumidityThreshold());
        }
        if (updateDevice.getAmbientHumidity() != null) {
            oldDevice.setAmbientHumidity(updateDevice.getAmbientHumidity());
        }
        if (updateDevice.getLastUpdateTime() != null) {
            oldDevice.setLastUpdateTime(updateDevice.getLastUpdateTime());
        }
        if (updateDevice.getAmbientTemperature() != null) {
            oldDevice.setAmbientTemperature(updateDevice.getAmbientTemperature());
        }


//        if (updateDevice.getUser().getId()!=null){
//            oldDevice.setUser(updateDevice.getUserId());
//        }

        if (updateDevice.getStartWateringHumidityThreshold() != 0) {
            oldDevice.setStartWateringHumidityThreshold(updateDevice.getStartWateringHumidityThreshold());
        }

        if (updateDevice.getIsOnline() != null) {
            oldDevice.setIsOnline(updateDevice.getIsOnline());
        }

        if (updateDevice.getLastUpdateTime() != null) {
            oldDevice.setLastUpdateTime(updateDevice.getLastUpdateTime());
        }
//        if (!updateDevice.getSchedule().getDailySchedule().isEmpty()){
//            oldDevice.setSchedule(updateDevice.getSchedule());
//        }

        if (updateDevice.getLastWateringTime() != null) {
            oldDevice.setLastWateringTime(updateDevice.getLastWateringTime());
        }

        if (updateDevice.getDescription() != null) {
            oldDevice.setDescription(updateDevice.getDescription());
        }

        if (updateDevice.getName() != null) {
            oldDevice.setName(updateDevice.getName());
        }

        if (updateDevice.getGpsLocation() != null) {
            oldDevice.setGpsLocation(updateDevice.getGpsLocation());
        }

        if (updateDevice.getGrowingType() != null) {
            oldDevice.setGrowingType(updateDevice.getGrowingType());
        }

        if (updateDevice.getWateringType() != null) {
            oldDevice.setWateringType(updateDevice.getWateringType());
        }

        deviceRepository.save(oldDevice);
    }

    public void deleteDevice(Integer deviceID, Integer userId) {
        Device device = getDeviceById(deviceID, userId);

        deviceRepository.delete(device);
    }

    public void startDevice(Integer deviceID, Integer userId) throws IOException {

        Device oldDevice = getDeviceById(deviceID, userId);
        oldDevice.setWateringPeriod(WateringPeriod.AWAIT_WATERING);
        oldDevice.setOffWatering(false);
        deviceRepository.save(oldDevice);

        // Send start message to device
//        WebSocketSession deviceSession = sessionManagementService.getSessionById(id);

//        if (deviceSession != null && deviceSession.isOpen()) {
//            deviceSession.sendMessage(prepareStatusDeviceJson(id,"Device Is Started"));
//        } else {
//            System.out.println("startElse");
//        }
    }

    public void stopDevice(Integer deviceID, Integer userId) throws IOException {
        Device oldDevice = getDeviceById(deviceID, userId);
        oldDevice.setWateringPeriod(WateringPeriod.STOPPED);
        oldDevice.setOffWatering(true);
        deviceRepository.save(oldDevice);

        // Send stop message to device
//        WebSocketSession deviceSession = sessionManagementService.getSessionById(id);
//        if (deviceSession != null && deviceSession.isOpen()) {
//            deviceSession.sendMessage(prepareStatusDeviceJson(id,"Device Is Stopped"));
//        } else {
//            System.out.println("stopElse");
//        }
    }

    public String prepareStatusDeviceJson(Device device, String message) {
        JSONObject status = new JSONObject();


        WateringPeriod wateringPeriod = device.getWateringPeriod();
        boolean wateringOnOff = (wateringPeriod == WateringPeriod.WATERING || wateringPeriod == WateringPeriod.AWAIT_WATERING);
        status.put("wateringOnOff", wateringOnOff);

        status.put("message", message);

        return status.toString();
    }


}
