package com.example.vehicle_tracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @SequenceGenerator(name = "owner_seq" , sequenceName = "owner_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq")
    private Integer id;
    private String ownerNames;
    private String nationalId;
    private String phoneNumber;
    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "owner"  , cascade = CascadeType.ALL , orphanRemoval = true )
    private List<Plate> plates;


    @OneToMany(mappedBy = "owner"  , cascade = CascadeType.ALL , orphanRemoval = true )
    private List<Vehicle> vehicles;


    public void addPlate(Plate plate){
        plates.add(plate);
        plate.setOwner(this);
    }
}
