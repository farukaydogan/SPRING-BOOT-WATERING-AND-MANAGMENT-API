package com.humidty.arge.controller.api;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.Sensor;
import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SensorService;
import com.humidty.arge.service.UserService;
import com.humidty.arge.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/sensor")

@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;
    private final DeviceService deviceService;
    private final UserService userService;


//        burada tum sensorlerin unique olanlari gelecek yani her sensorden bir tane ve son degerleri
    @GetMapping()
    public ResponseEntity<List<Sensor>> getAllsensors(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Device> devices = userService.getUserDevices(user.getId());
        List<Sensor> allSensors = new ArrayList<>();

        for (Device device : devices) {
            List<Sensor> deviceSensors = device.getSensors();
            if (deviceSensors != null && !deviceSensors.isEmpty()) {
                allSensors.addAll(deviceSensors);
            }
        }

        return new ResponseEntity<>(allSensors, HttpStatus.OK);
    }


    // kendi 10 adet son verisini ver
//    @GetMapping("/{id}")
//    public List<Sensor> getsensorByDeviceId(@PathVariable int id,) {
//        Device device = deviceService.getDeviceById(id);
//        List<String> sensorIds = device.getSensorIds();
//        List<Sensor> sensorList = new ArrayList<>();
//        for (String sensorId : sensorIds) {
//            Sensor sensor = sensorService.getsensorById(sensorId);
//            System.out.println(sensorId);
//            sensorList.add(sensor);
//        }
//        return sensorList;
//    }


    //    @GetMapping
//   public ResponseEntity<List<sensor>> getAllsensors(@AuthenticationPrincipal User user){
//    public ResponseEntity<List<Device>> getAllsensors(@AuthenticationPrincipal User user){
//        if (user == null || user.getDevices() == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//            List<Device> sensorList= new ArrayList<>(user.getDevices());
//
////        List<Device> sensorList = user.getDevices().stream()
//////                .filter(device -> device.getsensor() != null)
//////                .flatMap(device -> device.getsensor().stream())
////                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(sensorList, HttpStatus.OK);
////        return new ResponseEntity<>(new List<sensor>(), HttpStatus.OK);
//    }


//    @GetMapping("/{id}")
//    public ResponseEntity<List<sensor>> getsensorById(@PathVariable Integer id,
//                                                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
//                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
//
//        int defaultPageNumber = 0;
//        int defaultPageSize = 10;
//
//        if (pageNumber == null) {
//            pageNumber = defaultPageNumber;
//        }
//        if (pageSize == null) {
//            pageSize = defaultPageSize;
//        }
//
//        Page<sensor> sensorPage = sensorService.getsensorById(id, pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "date"));
//
//        List<sensor> sensorList = sensorPage.getContent();
//
//
//        if (!sensorList.isEmpty()) {
//            return new ResponseEntity<>(sensorList, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("{deviceId}/{sensorId}")
    public Sensor testSensorFind(Integer deviceId, Integer sensorId){
        return sensorService.getTest(deviceId,sensorId);
    }


    @PostMapping
    public ResponseEntity<Sensor> createsensor(@RequestBody Sensor newsensor, @AuthenticationPrincipal User user) {

        //        Integer sensorId = newsensor.getSensorId();
        Integer deviceId = newsensor.getDevice().getDeviceID();
        Integer userId = user.getId();
        Device device = deviceService.getDeviceById(deviceId, userId);
        Sensor createdsensor = sensorService.createSensor(newsensor);

        // Eğer Device bulunamadıysa hata fırlat
        if (device == null) {
            throw new RuntimeException("Device Not Found");
        }



        return new ResponseEntity<>(createdsensor, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        sensorService.deleteAll();
        return new ResponseEntity<>("Deleted", OK);
    }
}
