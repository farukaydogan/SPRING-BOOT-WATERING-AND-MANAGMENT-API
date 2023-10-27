package com.humidty.arge.helper;

import lombok.Getter;

@Getter
public enum WateringPeriod {
    AWAIT_WATERING("Awaiting Watering"),
    WATERING("Watering"),
    STOPPED("Device Stopped"),
    AWAIT_SATURATION("Saturation Reached, Awaiting Drying");
    private final String displayName;

    WateringPeriod(String displayName) {
        this.displayName = displayName;
    }

}