package com.humidty.arge.helper;

import lombok.Getter;

@Getter
public enum WateringType {
    DRIP("Damlama"),
    INTFLATABLE("Sisme");
    private final String displayName;

    WateringType(String displayName) {
        this.displayName = displayName;
    }

}