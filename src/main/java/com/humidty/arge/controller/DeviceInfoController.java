package com.humidty.arge.controller;

import com.humidty.arge.model.*;
import com.humidty.arge.service.DeviceService;
import com.humidty.arge.service.SensorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/device-info")
@AllArgsConstructor
public class DeviceInfoController {


    private final DeviceService deviceService;
    private final SensorService sensorService;

    @GetMapping("/{deviceId}")
    public String deviceDetail(@PathVariable("deviceId") Integer deviceId, Model model, @AuthenticationPrincipal User user) {
        Device device = deviceService.getDeviceById(deviceId, user.getId());

        if (device == null) {
            return "redirect:/error";
        }

        List<Sensor> sensors = device.getSensors();
        List<SensorDTO> sensorDTOs = new ArrayList<>();

        for (Sensor sensor : sensors) {
            SensorNutrient lastNutrient = sensor.getLastSensorNutrient();
            SensorDTO sensorDTO = new SensorDTO(sensor, lastNutrient);
            sensorDTOs.add(sensorDTO);
        }

        model.addAttribute("device", device);
        model.addAttribute("sensorDTOs", sensorDTOs);



        // Model'e JWT tokenini ekle
//        model.addAttribute("jwtToken", jwtToken);

        return "deviceInfo";

    }

}
