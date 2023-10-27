package com.humidty.arge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class SensorNutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    @JsonIgnore
    private Sensor sensor;

    private Integer deviceId;
    private int humidity;
    private double ec;
    private double pH;
    private int nitrogen;
    private double potassium;
    private double phosphorus;

    private Date createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date(); // Bu metot, entity kaydedilirken otomatik olarak tarih oluşturacaktır.
    }
}
