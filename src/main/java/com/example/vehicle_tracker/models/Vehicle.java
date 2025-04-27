package com.example.vehicle_tracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @SequenceGenerator(name = "vehicle-seq" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle-seq")
    private Integer vehicleId;
    private int chasisNumber;
    private String manufacturer;
    private int year;
    private int price;
}
