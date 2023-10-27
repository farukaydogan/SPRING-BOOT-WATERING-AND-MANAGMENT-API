package com.humidty.arge.helper;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Embeddable
public class Schedule {


    @ElementCollection
    @MapKeyColumn(name = "day")
    @Column(name = "status")
    @CollectionTable(name = "dailySchedule", joinColumns = @JoinColumn(name = "deviceId"))

    private Map<String, Boolean> dailySchedule;

    public Schedule() {
        dailySchedule = new HashMap<>();
    }

    // getter ve setter methodları
    public Map<String, Boolean> getDailySchedule() {
        return dailySchedule;
    }

    // Tüm saatlerin değerlerini true yap
    public void initializeAsTrue() {
        for (int i = 0; i < 24; i++) {
            dailySchedule.put(String.format("%02d", i), true);
        }
    }

    public void setDailySchedule(Map<String, Boolean> dailySchedule) {
        this.dailySchedule = dailySchedule;
    }
}
