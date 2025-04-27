package com.example.vehicle_tracker.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String country;
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String village;
}
