package com.example.vehicle_tracker.services;

import com.example.vehicle_tracker.dto.ErrorResponse;
import com.example.vehicle_tracker.dto.PlateRequest;
import com.example.vehicle_tracker.models.Plate;
import com.example.vehicle_tracker.repositories.PlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlateService {
    private final PlateRepository plateRepository;
    public ResponseEntity<?> addPlate(PlateRequest plateRequest) {
        Optional<Plate> plateExists = plateRepository.findPlateByPlateNumber(plateRequest.getPlateNumber());
        if (plateExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponse.builder()
                            .message("Plate already exists")
                            .statusCode(HttpStatus.CONFLICT.value())
                            .build()
            );
        }

        Plate plate = Plate
                .builder()
                .plateNumber(plateRequest.getPlateNumber())
                .issuedDate(plateRequest.getIssuedDate())
                .build();

        var savedPlate = plateRepository.save(plate);
        System.out.println(savedPlate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlate);
    }
}
