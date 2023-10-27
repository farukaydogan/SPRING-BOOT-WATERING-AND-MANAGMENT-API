package com.humidty.arge.service;

import com.humidty.arge.model.Device;
import com.humidty.arge.model.Sensor;
import com.humidty.arge.model.SensorNutrient;
import com.humidty.arge.model.User;
import com.humidty.arge.repository.SensorNutrientRepository;
import com.humidty.arge.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class SensorService {

    private  final SensorRepository sensorRepository;
    private final SensorNutrientRepository sensorNutrientRepository;


    public Sensor getTest(Integer deviceId,Integer sensorId){
        return sensorRepository.findBySensorIdAndDevice_deviceID(sensorId,deviceId);
    }
    public List<Sensor> getsensorsAll() {
        return sensorRepository.findAll();
    }

    public Sensor getSensorByID(Integer  sensorId) {
        return sensorRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Device Not Found"));
    }

    public List<SensorNutrient> getLastTenSensorNutrient(Device device) {

        // burada tum sensorlerin son 10 degeri gelecek sekilde ayarlanmalidir
        //TODO // Tüm sensörleri getir (bu kısımda optimizasyon yapılabilir)
        List<Sensor> allSensors = device.getSensors();

        // Her sensör için son 10 değeri alıp tek bir liste halinde dön
        return allSensors.stream()
                .flatMap(sensor -> sensor.getSensorNutrients().stream()
                        .sorted((sn1, sn2) -> sn2.getCreateTime().compareTo(sn1.getCreateTime())) // En yeni veriyi en üstte almak için sırala
                        .limit(10)) // Son 10 veriyi al
                .collect(Collectors.toList());
    }

    public Optional<SensorNutrient> getLastNpkValues(Device device){
        return sensorNutrientRepository.findTopBySensor_Device_DeviceIDAndPhIsNotNullOrderByCreateTimeDesc(device.getDeviceID());
    }

//    public Page<sensor> getsensorById(Integer deviceId, Integer pageNumber, Integer pageSize, Sort sort) {
//        int defaultPageNumber = 0;
//        int defaultPageSize = 10;
//
//        if (pageNumber == null) {
//            pageNumber = defaultPageNumber;
//        }
//        if (pageSize == null) {
//            pageSize = defaultPageSize;
//        }
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        return sensorRepository.findByDeviceId(deviceId, pageable);
//    }

//    public sensor getsensorById(Integer deviceId) {
//        return sensorRepository.findByDeviceId(deviceId);
//    }

//    public SensorNutrient getLatestSensorNutrientBySensorId(Integer sensorId) {
//        List<SensorNutrient> sensorNutrients = sensorNutrientRepository.findLatestBySensorId(sensorId);
//        return sensorNutrients.isEmpty() ? null : sensorNutrients.get(0);
//    }

    public Sensor createSensor(Sensor newsensor) {
        // deviceId'ye göre Device'ı bul

        // Yeni sensor'ı kaydet ve döndür
        return sensorRepository.save(newsensor);
    }
    public void deleteAll(){
        sensorRepository.deleteAll();
    }
}
