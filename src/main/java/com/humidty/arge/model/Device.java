package com.humidty.arge.model;

//import javax.persistence.PreUpdate;
//import javax.persistence.PrePersist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.humidty.arge.helper.GrowingType;
import com.humidty.arge.helper.WateringPeriod;
import com.humidty.arge.helper.WateringType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Device {

//    @PreUpdate
//    @PrePersist
//    public void updateTimestamps() {
//        lastUpdateTime = new Date();
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer deviceID;


    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Sensor> sensors = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    private String name;
    private String description;
    private Boolean offWatering;
    private Integer ambientHumidity;
    private Integer ambientTemperature;
    private Boolean isOnline;
    private WateringPeriod wateringPeriod;
    private GrowingType growingType;
    private WateringType wateringType;

    private Integer batteryStatus;
    private String gpsLocation;

    private Integer percentYield;
    private int startWateringHumidityThreshold;
    private int stopWateringHumidityThreshold;
//    private List<Integer> sensorIds;

    private Date lastWateringTime;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date", updatable = false)
    private Date createTime;
    private Date lastUpdateTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date(); // Bu metot, entity kaydedilirken otomatik olarak tarih oluşturacaktır.
    }


//    @Embedded
//    private Schedule schedule;

//    public Device() {
//        schedule = new Schedule();
//        schedule.initializeAsTrue();
//    }

    // Getters and setters...

//    public List<Integer> getSensorIds() {
//        return sensorIds;
//    }

//    public void setSensorIds(List<Integer> sensorIds) {
//        this.sensorIds = sensorIds;
//    }


}
