package com.humidty.arge.service;

import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.model.Device;
import com.humidty.arge.model.User;
import com.humidty.arge.repository.DeviceRepository;
import com.humidty.arge.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {


    private UserRepository userRepository;
    private DeviceRepository deviceRepository;


    @Transactional(readOnly = true)
    public List<Device> getUserDevices(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            List<Device> devices = user.getDevices();
            devices.size(); // Bu satır, Hibernate'i devices koleksiyonunu yüklemeye zorlar
            return devices;
        }
        return Collections.emptyList();
    }

    public Device findByUserTokenAndDeviceId(String userToken, Integer deviceId) {
        User user = userRepository.findByUserToken(userToken)
                .orElseThrow(() -> new RuntimeException("User not found with the given token"));

        return user.getDevices().stream()
                .filter(device -> device.getDeviceID().equals(deviceId))
                .findFirst().orElseGet(() -> {
            Device newDevice = new Device();
            // newDevice için gerekli ayarlamaları yapın
            user.getDevices().add(newDevice);
            newDevice.setUser(user);
            newDevice.setIsOnline(true);
            newDevice.setWateringPeriod(WateringPeriod.AWAIT_WATERING);
            newDevice.setOffWatering(false);
            userRepository.save(user);
            deviceRepository.save(newDevice);
            return newDevice;
        });

    }

    public Optional<User> getUserById(Integer userId){
        return userRepository.findById(userId);
    }
    //
//    public User getUserById(Integer userId) {
//        User defaultUser = new User(); // varsayılan bir User nesnesi oluşturun.
//        return userRepository.findById(userId).orElse(defaultUser);
//    }
    public List<User> findAll(){
        return userRepository.findAll();
    }


}