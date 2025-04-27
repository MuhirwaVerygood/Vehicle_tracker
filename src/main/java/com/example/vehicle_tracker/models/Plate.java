package com.example.vehicle_tracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plate {
    @Id
    @SequenceGenerator(name = "plate-seq" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "plate-seq")
    private Integer plateId;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id",  nullable = false)
    private Owner owner;
    private String plateNumber;
    private Date issuedDate;
}
