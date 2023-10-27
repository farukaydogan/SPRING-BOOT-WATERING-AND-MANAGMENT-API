package com.humidty.arge.controller.api;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.Sensor;
import com.humidty.arge.model.SensorNutrient;
import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SensorNutrientService;
import com.humidty.arge.service.SensorService;
import com.humidty.arge.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1//sensor-nutrient")
@RequiredArgsConstructor
public class SensorNutrientController {

    private  final SensorNutrientService sensorNutrientService;
    private  final DeviceService deviceService;
    private  final SensorService sensorService;

    @PostMapping
    public ResponseEntity<SensorNutrient> sensorNutrientPost(@AuthenticationPrincipal User user,@RequestBody SensorNutrient sensorNutrient) {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        Integer deviceId = sensorNutrient.getSensor().getDevice().getDeviceID();

        // Step 2: Check if the user has the given Device
        Device userDevice = deviceService.getDeviceById(deviceId, user.getId());

        if (userDevice == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Integer sensorId = sensorNutrient.getSensor().getSensorId();

        Sensor sensor = sensorService.getSensorByID(sensorId);

        if (sensor == null) {
            sensor = new Sensor();
            sensor.setSensorId(sensorId);
            sensor.setDevice(userDevice);
            sensorService.createSensor(sensor);
        }


       SensorNutrient newSensorNutrient= sensorNutrientService.createSensorNutrient(sensorNutrient,deviceId);

       return new ResponseEntity<>(newSensorNutrient, OK);
        // Step 3: Check if the Sensor already exists

    }


    @DeleteMapping
    public ResponseEntity<String>deleteAll(){
        sensorNutrientService.deleteAll();
        return new ResponseEntity<>("Deleted", OK);
    }
}
