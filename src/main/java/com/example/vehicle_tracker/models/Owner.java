package com.example.vehicle_tracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @SequenceGenerator(name = "owner_seq" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq")
    private Integer id;
    private String ownerNames;
    private int nationalId;
    private int phoneNumber;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "owner")
    private List<Plate> plates;
}
