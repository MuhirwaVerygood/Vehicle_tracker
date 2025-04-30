package com.example.vehicle_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlateRequest {
    @NotBlank(message = "Plate number is required")
    @Pattern(regexp = "^[A-Z0-9]{1,10}$", message = "Invalid plate number format")
    private String plateNumber;

    @NotNull(message = "Issued date is required")
    private Date issuedDate;
}