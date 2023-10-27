package com.humidty.arge.helper;

import com.humidty.arge.model.Device;
import com.humidty.arge.repository.SensorNutrientRepository;
import com.humidty.arge.repository.SensorRepository;
import com.humidty.arge.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final DeviceService deviceService;
    private final SensorNutrientRepository sensorNutrientRepository;

    @Scheduled(fixedRate = 60000) // Her dakika (60 * 1000 milisaniye)
    public void deviceStatusCheck() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -30);
        Date thirtyMinutesAgo = calendar.getTime();

        // Son 30 dakika içinde güncellenen cihazları online yap
        List<Device> recentlyUpdatedDevices = deviceService.getDevicesLastUpdatedAfter(thirtyMinutesAgo);
        for (Device device : recentlyUpdatedDevices) {
            if (!device.getIsOnline()) {
                device.setIsOnline(true);
                deviceService.updateDevice(device);
            }
        }

        // Son 30 dakika içinde güncellenmeyen cihazları offline yap
        List<Device> notRecentlyUpdatedDevices = deviceService.getDevicesLastUpdatedBefore(thirtyMinutesAgo);
        for (Device device : notRecentlyUpdatedDevices) {
            if (device.getIsOnline()) {
                device.setIsOnline(false);
                deviceService.updateDevice(device);
            }
        }
    }


    @Scheduled(cron = "0 0 2 * * ?")  // Her gün saat 02:00'de çalışacak
    public void cleanupOldData() {
        System.out.println("Temizlik baslatildi");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);  // Bir gün geriye git
        Date oneDayAgo = calendar.getTime();

        sensorNutrientRepository.deleteOldSensorNutrients(oneDayAgo);
    }
}