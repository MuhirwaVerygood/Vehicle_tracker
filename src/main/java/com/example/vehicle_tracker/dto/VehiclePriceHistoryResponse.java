package com.example.vehicle_tracker.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiclePriceHistoryResponse {
    private LocalDateTime date;
    private BigDecimal price;
    private String ownerName;
}