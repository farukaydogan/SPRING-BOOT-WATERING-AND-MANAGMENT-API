package com.humidty.arge.controller.api;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.SensorNutrient;
import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SensorService;
import com.humidty.arge.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final SensorService sensorService;



    @GetMapping
    public ResponseEntity<List<Device>> getDevice(@AuthenticationPrincipal User user) {
        Integer userId = user.getId(); // Kullanıcı adı (veya ID) oturum bilgilerinden alınır.
        List<Device> devices = deviceService.getDevicesByUserId(userId);

        return new ResponseEntity<>(devices, HttpStatus.OK);
    }


    @GetMapping("/{deviceID}")

    public ResponseEntity<Device> getDevice(@PathVariable Integer  deviceID,@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(deviceService.getDeviceById(deviceID,user.getId()), OK);
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device newDevice,@AuthenticationPrincipal User user) {
        newDevice.setUser(user);
        return new ResponseEntity<>(deviceService.createDevice(newDevice), CREATED);
    }


    @PutMapping("{deviceID}")
    public ResponseEntity<Device> updateDevice(@PathVariable Integer deviceID, @RequestBody Device updateDevice,@AuthenticationPrincipal User user) {
        updateDevice.setUser(user);
        updateDevice.setDeviceID(deviceID);
        deviceService.updateDevice(updateDevice);
        return new ResponseEntity<>(deviceService.getDeviceById(deviceID,user.getId()), OK);
    }


    @DeleteMapping("{deviceID}")
    public ResponseEntity<String> deleteDevice(@PathVariable Integer deviceID,@AuthenticationPrincipal User user) {
        deviceService.deleteDevice(deviceID,user.getId());
        return new ResponseEntity<>("Device deleted", OK);
    }

    @GetMapping("stop/{deviceID}")
    public ResponseEntity<String> stopDevice(@PathVariable Integer deviceID,@AuthenticationPrincipal User user) throws IOException {
        deviceService.stopDevice(deviceID,user.getId());
        //gerekli socket mesahi gonderilir
        return new ResponseEntity<>("Device stopped", OK);
    }

    @GetMapping("start/{deviceID}")
    public ResponseEntity<String> startDevice(@PathVariable Integer deviceID,@AuthenticationPrincipal User user) throws IOException {
        deviceService.startDevice(deviceID,user.getId());
        //gerekli socket mesahi gonderilir
        return new ResponseEntity<>("Device started", OK);
    }


    @GetMapping("/getLatestTenSensorData/{deviceId}")
    public @ResponseBody List<SensorNutrient> getLatestTenSensorData(@PathVariable("deviceId") Integer deviceId , @AuthenticationPrincipal User user) {
        Device device = deviceService.getDeviceById(deviceId, user.getId());
//
//        if (device == null) {
//            return "redirect:/error";
//        }

        return sensorService.getLastTenSensorNutrient(device);
    }


    @GetMapping("/getLatestNpkValues/{deviceId}")
    public @ResponseBody Optional<SensorNutrient> getLatestNpkValues(@PathVariable("deviceId") Integer deviceId , @AuthenticationPrincipal User user) {
        Device device = deviceService.getDeviceById(deviceId, user.getId());

        //        if (device == null) {
//            return "redirect:/error";
//        }

        return sensorService.getLastNpkValues(device);
    }

}

