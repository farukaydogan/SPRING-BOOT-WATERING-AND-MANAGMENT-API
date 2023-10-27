package com.humidty.arge.model;

import lombok.Data;

@Data
public class SensorDTO {
    private Sensor sensor;
    private SensorNutrient lastSensorNutrient;

    public SensorDTO(Sensor sensor, SensorNutrient lastNutrient) {
        this.sensor=sensor;
        this.lastSensorNutrient=lastNutrient;
    }

    // Getter, Setter ve Constructor metotlarını ekleyin
}