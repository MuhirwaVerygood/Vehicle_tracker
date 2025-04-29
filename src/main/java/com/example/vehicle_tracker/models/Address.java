package com.example.vehicle_tracker.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String country;
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String village;
}
