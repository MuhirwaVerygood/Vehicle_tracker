package com.example.vehicle_tracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plate {
    @Id
    @SequenceGenerator(name = "plate-seq" , sequenceName = "plate-seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "plate-seq")
    private Integer plateId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id",  nullable = false)
    private Owner owner;
    private String plateNumber;
    private Date issuedDate;

    @OneToOne
    @JoinColumn(name = "vehicle-id" , referencedColumnName = "vehicleId")
    private Vehicle vehicle;
}

