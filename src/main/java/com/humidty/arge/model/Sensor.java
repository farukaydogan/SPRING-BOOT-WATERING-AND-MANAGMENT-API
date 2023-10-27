package com.humidty.arge.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Sensor{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        private Integer sensorId;

        //        one to one olacak
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "device_id")
        @JsonIgnore
        private Device device;


        @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonIgnore
        private List<SensorNutrient> sensorNutrients = new ArrayList<>();

        private Date createTime;
        private Date lastUpdateTime;

        @PrePersist
        public void prePersist() {
                this.createTime = new Date(); // Bu metot, entity kaydedilirken otomatik olarak tarih oluşturacaktır.
        }
        @PreUpdate
        public void preUpdate(){
                this.lastUpdateTime=new Date();
//                lastSensorNutrient=getLastSensorNutrient();
        }
        public SensorNutrient getLastSensorNutrient() {
                return sensorNutrients.stream()
                        .max(Comparator.comparing(SensorNutrient::getCreateTime))
                        .orElse(null);
        }




        // Constructor, getters, setters...
}
