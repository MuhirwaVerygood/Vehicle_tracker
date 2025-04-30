package com.example.vehicle_tracker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOwnerRequest {
    private int vehicleId;
    private int plateId;   
    // Owner names: 2-50 chars, only letters/spaces/hyphens, no leading/trailing spaces
    @NotBlank(message = "Owner names are required")
    @Size(min = 2, max = 50, message = "Owner names must be 2-50 characters")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*$",
            message = "Owner names can only contain letters, spaces, or hyphens"
    )
    private String ownerNames;

    // National ID: Exactly 16 digits, starts with 1
    @NotNull(message = "National ID is required")
    @Pattern(
            regexp = "^1\\d{15}$",
            message = "National ID must be 16 digits starting with 1"
    )
    private String nationalId;

    // Phone number: Starts with 078/079 followed by 7 digits (10 total)
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(078|079)\\d{7}$",
            message = "Phone must start with 078 or 079 followed by 7 digits"
    )
    private String phoneNumber;

    @Valid
    @NotNull(message = "Address is required")
    private AddressRequest address;
}