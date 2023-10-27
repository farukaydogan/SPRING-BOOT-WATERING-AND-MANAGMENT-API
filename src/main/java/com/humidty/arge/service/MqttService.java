package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.model.Device;
import com.humidty.arge.model.SensorNutrient;
import com.humidty.arge.model.Sensor;
import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MqttService {

    private final MqttClient mqttClient;
    private final DeviceService deviceService;
    private final SensorService sensorService;
    private final SensorNutrientService sensorNutrientService;

    public MqttService(MqttClient mqttClient, @Lazy DeviceService deviceService, SensorService sensorService, SensorNutrientService sensorNutrientService) throws MqttException {
        this.mqttClient = mqttClient;
        this.deviceService = deviceService;
        this.sensorNutrientService = sensorNutrientService;
        this.sensorService = sensorService;
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                // Handle connection loss...
                System.out.println("baglanti koptu");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws MqttException {

                if (topic.equals(topic + "/status")) {
                    System.out.println("Status update from " + topic + ": " + new String(message.getPayload()));
                }
                // This method is called when a message arrives
//                System.out.println("Message received: " + new String(message.getPayload()));
                // get payload from message
                byte[] bytes = message.getPayload();


                String payload = new String(bytes, StandardCharsets.UTF_8);


                JSONObject jsonObj = new JSONObject(payload);

//                System.out.println(payload);
                // JSON objesinden deviceID ve humidity değerlerini alın
                Integer deviceID = jsonObj.optInt("deviceID", 0); // Anahtar bulunamazsa ya da değer null ise 0 döndür.


                JSONArray sensorList = jsonObj.getJSONArray("sensorList");

                String userToken= jsonObj.getString("userToken");

                Device device = deviceService.getDeviceByIdOnlyUsesMqtt(deviceID,userToken);

                device.setAmbientHumidity(jsonObj.getInt("ambientHumidity"));
                device.setAmbientTemperature(jsonObj.getInt("ambientTemperature"));
                device.setLastUpdateTime(new Date());



                String responseMessage = handeMqttData(device, sensorList);

                System.out.println(responseMessage);
                byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);

//                 Yeni bir MqttMessage oluşturun ve yayın yapın
                MqttMessage mqttMessage = new MqttMessage(responseBytes);
                mqttClient.publish("response_topic", mqttMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                System.out.println("mesaj gonderildi");
            }

        });

        try {
            // Subscribe to the topic 'test_topic'
            mqttClient.subscribe("test_topic");
            System.out.println("alo start");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void onOffInfoUpdateDevice(boolean onOrOf, Integer deviceId,Integer userId) {
        Device old = deviceService.getDeviceById(deviceId,userId);
        old.setDeviceID(deviceId);
        old.setIsOnline(onOrOf);
        deviceService.updateDevice(old);
    }

    public String handeMqttData(Device device, JSONArray sensorList) {


        if (device == null) {
            throw new RuntimeException("Device Not Found");
        }

        WateringPeriod wateringPeriod = device.getWateringPeriod();
        int startWateringHumidityThreshold = device.getStartWateringHumidityThreshold();
        int stopWateringHumidityThreshold = device.getStopWateringHumidityThreshold();
        List<Integer> humidityList = new ArrayList<>();
        Integer deviceId= device.getDeviceID();



        for (int i = 0; i < sensorList.length(); i++) {
            JSONObject sensorJson = sensorList.getJSONObject(i);
            Integer sensorSensorId = sensorJson.getInt("sensorId");
//            deviceService.getDeviceByIdOnlyUsesMqtt(deviceId);
            //device id ile sensorlere eris sensor listesinde sensor varsa id al ona nutrient ekle
            //sensor yoksa yeni sensor  olustur ona yeni nutrient data pushla


            Sensor sensor =  deviceService.getSensorBySensorSensorId(device,sensorSensorId);

            int humidity = sensorJson.getInt("humidity");
            humidityList.add(humidity);

            SensorNutrient sensorNutrient = new SensorNutrient();
            sensorNutrient.setHumidity(humidity);
            sensorNutrient.setDeviceId(deviceId);


            if (!sensorJson.isNull("sensorNutrient")) {
                JSONObject sensorNutrientJson = sensorJson.getJSONObject("sensorNutrient");

                sensorNutrient.setSensor(sensor);
                sensorNutrient.setEc(sensorNutrientJson.getDouble("ec"));
                sensorNutrient.setPH(sensorNutrientJson.getDouble("pH"));
                sensorNutrient.setNitrogen(sensorNutrientJson.getInt("nitrogen"));
                sensorNutrient.setPotassium(sensorNutrientJson.getDouble("potassium"));
                sensorNutrient.setPhosphorus(sensorNutrientJson.getDouble("phosphorus"));
//                sensor.setSensorNutrients(sensorNutrient);
            }

            sensorNutrient = sensorNutrientService.createSensorNutrient(sensorNutrient,deviceId,sensor.getSensorId());
            deviceService.updateDevice( device);

//            System.out.println(sensor);

        }

        if (device.getOffWatering()){
            device.setWateringPeriod(WateringPeriod.STOPPED);
        }
        else {
            device.setWateringPeriod(WateringPeriod.AWAIT_WATERING);
        }
        // nem değerleri listesi kontrolü
        boolean allAboveThreshold = humidityList.stream()
                .allMatch(humidity -> humidity > stopWateringHumidityThreshold);

        boolean allBelowThreshold = humidityList.stream()
                .allMatch(humidity -> humidity < startWateringHumidityThreshold);

        if (wateringPeriod != WateringPeriod.STOPPED) {
            if (wateringPeriod == WateringPeriod.AWAIT_WATERING && allAboveThreshold) {
                device.setWateringPeriod(WateringPeriod.WATERING);
                System.out.println("alo WATERING");
            } else if (wateringPeriod == WateringPeriod.WATERING && allBelowThreshold) {
                device.setWateringPeriod(WateringPeriod.AWAIT_SATURATION);
                System.out.println("alo AWAIT_SATURATION");
            } else if (wateringPeriod == WateringPeriod.AWAIT_SATURATION && allAboveThreshold) {
                device.setWateringPeriod(WateringPeriod.AWAIT_WATERING);
                System.out.println("alo AWAIT_WATERING");
            }
        }

        device.setLastUpdateTime(new Date());
        device.setDeviceID(deviceId);
        deviceService.updateDevice( device);

        return deviceService.prepareStatusDeviceJson(device, "handle success");
    }

}
