package com.example.vehicle_tracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String chasisNumber;
    private String manufacturer;
    private int year;
    private int price;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "owner_id" , referencedColumnName = "id" , nullable = true)
    private Owner owner;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "plate_id" , referencedColumnName = "plateId" , nullable = true)
    private  Plate plate;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<OwnershipHistory> ownershipHistory;
}




