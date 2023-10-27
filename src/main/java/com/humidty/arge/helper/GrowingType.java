package com.humidty.arge.helper;

public enum GrowingType {
    GREENHOUSE("Sera"),
    FIELD("Tarla");
    private final String displayName;

    GrowingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
