package com.example.vehicle_tracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferOwnershipRequest {
    private int vehicleId;
    private int newOwnerId;

    @NotNull
    @Positive
    private int transferPrice;
}