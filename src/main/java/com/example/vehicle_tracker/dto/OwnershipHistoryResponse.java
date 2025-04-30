package com.example.vehicle_tracker.dto;

import com.example.vehicle_tracker.models.OwnershipHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnershipHistoryResponse {
    private Long id;
    private Long ownerId;
    private String ownerName;
    private String ownerNationalId;
    private String ownerPhone;
    private Long vehicleId;
    private String vehicleDetails;
    private Long plateId;
    private String plateNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean current;
    private String duration; // Calculated field

    public static OwnershipHistoryResponse fromEntity(OwnershipHistory history) {
        return OwnershipHistoryResponse.builder()
                .id(Long.valueOf(history.getId()))
                .ownerId(Long.valueOf(history.getOwner().getId()))
                .ownerName(history.getOwner().getOwnerNames())
                .ownerNationalId(history.getOwner().getNationalId())
                .ownerPhone(history.getOwner().getPhoneNumber())
                .vehicleId(Long.valueOf(history.getVehicle().getVehicleId()))
                .vehicleDetails(history.getVehicle().getManufacturer() + " " +
                        history.getVehicle().getPrice() + " (" +
                        history.getVehicle().getYear() + ")")
                .plateId(Long.valueOf(history.getPlate().getPlateId()))
                .plateNumber(history.getPlate().getPlateNumber())
                .startDate(history.getStartDate())
                .endDate(history.getEndDate())
                .current(history.isCurrentOwnership())
                .duration(calculateDuration(history.getStartDate(), history.getEndDate()))
                .build();
    }

    private static String calculateDuration(LocalDateTime start, LocalDateTime end) {
        if (end == null) return "Present";

        long days = ChronoUnit.DAYS.between(start, end);
        if (days < 30) return days + " days";

        long months = ChronoUnit.MONTHS.between(start, end);
        if (months < 12) return months + " months";

        long years = ChronoUnit.YEARS.between(start, end);
        return years + " years";
    }
}