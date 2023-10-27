package com.humidty.arge.service;

import com.humidty.arge.model.Sensor;
import com.humidty.arge.model.SensorNutrient;
import com.humidty.arge.repository.SensorNutrientRepository;
import com.humidty.arge.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorNutrientService {

    private final SensorNutrientRepository sensorNutrientRepository;
    private final SensorRepository sensorRepository;

    public List<SensorNutrient> getAllNutrient(){
        return sensorNutrientRepository.findAll();
    }

    public SensorNutrient createSensorNutrient(SensorNutrient sensorNutrient,Integer deviceId,Integer sensorSensorId) {

        // Step 1: Check if the Sensor already exists
        Sensor sensor = sensorRepository.findBySensorIdAndDevice_deviceID(sensorSensorId,deviceId);

        // Step 2: If the Sensor does not exist, create a new Sensor
        if (sensor == null) {
            sensor = new Sensor();
            sensor.setSensorId(sensorSensorId);
//            sensor.setDevice(sensorNutrient.getSensor().getDevice());
            sensor = sensorRepository.save(sensor);
        }

        // Step 3: Add the SensorNutrient data to the Sensor and save it
        sensorNutrient.setSensor(sensor);
        return sensorNutrientRepository.save(sensorNutrient);
    }
    public SensorNutrient createSensorNutrient(SensorNutrient sensorNutrient,Integer deviceId){
       return createSensorNutrient(sensorNutrient,deviceId,sensorNutrient.getDeviceId());
    }

    public SensorNutrient getSensorNutrientById(Integer sensorId){
        return sensorNutrientRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Device Not Found"));
    }


    public void deleteAll(){
        sensorNutrientRepository.deleteAll();
    }
}
