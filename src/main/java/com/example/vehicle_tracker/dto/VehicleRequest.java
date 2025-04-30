package com.example.vehicle_tracker.dto;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class VehicleRequest {

    @NotBlank(message = "Chassis number is required")
    @Size(min = 17, max = 17, message = "Chassis number must be exactly 17 characters")
    private String chasisNumber;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @Min(value = 1886, message = "Year must be a valid manufacturing year")
    @Max(value = 2100, message = "Year must not exceed 2100")
    private int year;

    @Positive(message = "Price must be a positive number")
    private int price;
}
