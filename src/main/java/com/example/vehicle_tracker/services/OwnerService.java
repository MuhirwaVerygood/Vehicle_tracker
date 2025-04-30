package com.example.vehicle_tracker.services;

import com.example.vehicle_tracker.dto.*;
import com.example.vehicle_tracker.models.*;
import com.example.vehicle_tracker.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final VehicleRepository vehicleRepository;
    private final PlateRepository plateRepository;
    private final OwnershipHistoryRepository ownershipHistoryRepository;

    @Transactional
    public ResponseEntity<?> createOwner(CreateOwnerRequest request) {
        try {
            if (request.getAddress() == null) {
                return ResponseEntity.badRequest().body(
                        ErrorResponse.builder()
                                .message("Address is required")
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }

            Optional<Plate> plateExists = plateRepository.findById(request.getPlateId());
            if (!plateExists.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ErrorResponse.builder()
                                .message("Plate does not exist")
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build());
            }

            Optional<Vehicle> vehicleExists = vehicleRepository.findById(request.getVehicleId());
            if (!vehicleExists.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ErrorResponse.builder()
                                .message("Vehicle does not exist")
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build());
            }

            var owner = Owner.builder()
                    .ownerNames(request.getOwnerNames())
                    .address(request.getAddress().toAddress())
                    .nationalId(request.getNationalId())
                    .phoneNumber(request.getPhoneNumber())
                    .build();

            var savedOwner = ownerRepository.save(owner);

            Plate plate = plateExists.get();
            Vehicle vehicle = vehicleExists.get();

            plate.setOwner(savedOwner);
            plate.setVehicle(vehicle);
            vehicle.setOwner(savedOwner);
            vehicle.setPlate(plate);

            plateRepository.save(plate);
            vehicleRepository.save(vehicle);

            createOwnershipHistory(savedOwner, vehicle, plate);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.builder()
                            .message("Failed to create owner: " + e.getMessage())
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }


    @Transactional
    public ResponseEntity<?> transferOwnership(TransferOwnershipRequest request) {
        try {
            Optional<Owner> newOwner = ownerRepository.findById(request.getNewOwnerId());
            if (!newOwner.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ErrorResponse.builder()
                                .message("New owner not found")
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build());
            }

            Optional<Vehicle> vehicle = vehicleRepository.findById(request.getVehicleId());
            if (!vehicle.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ErrorResponse.builder()
                                .message("Vehicle not found")
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build());
            }

            // Close current ownership history
            OwnershipHistory currentHistory = ownershipHistoryRepository
                    .findByVehicleAndCurrentOwnership(vehicle.get(), true)
                    .orElseThrow(() -> new RuntimeException("No current ownership record found"));

            currentHistory.setEndDate(LocalDateTime.now());
            currentHistory.setCurrentOwnership(false);
            ownershipHistoryRepository.save(currentHistory);

            // Create new ownership history with transfer price
            OwnershipHistory newHistory = OwnershipHistory.builder()
                    .owner(newOwner.get())
                    .vehicle(vehicle.get())
                    .plate(vehicle.get().getPlate())
                    .startDate(LocalDateTime.now())
                    .currentOwnership(true)
                    .transferPrice(BigDecimal.valueOf(request.getTransferPrice()))
                    .build();
            ownershipHistoryRepository.save(newHistory);

            // Update vehicle and plate relationships
            vehicle.get().setOwner(newOwner.get());
            vehicle.get().getPlate().setOwner(newOwner.get());
            vehicleRepository.save(vehicle.get());
            plateRepository.save(vehicle.get().getPlate());

            return ResponseEntity.ok("Ownership transferred successfully with price: " + request.getTransferPrice());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.builder()
                            .message("Transfer failed: " + e.getMessage())
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }


    public ResponseEntity<?> getOwnershipHistory(Long vehicleId) {
        try {
            List<OwnershipHistory> history = ownershipHistoryRepository.findFullHistoryByVehicleId(vehicleId);
            List<OwnershipHistoryResponse> response = history.stream()
                    .map(OwnershipHistoryResponse::fromEntity)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.builder()
                            .message("Failed to get history: " + e.getMessage())
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }

    public ResponseEntity<?> getCurrentOwner(Long vehicleId) {
        try {
            OwnershipHistory current = ownershipHistoryRepository
                    .findByVehicleIdAndCurrentOwnership(vehicleId, true)
                    .orElseThrow(() -> new RuntimeException("No current owner found"));
            return ResponseEntity.ok(OwnershipHistoryResponse.fromEntity(current));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ErrorResponse.builder()
                            .message(e.getMessage())
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
    }

    public ResponseEntity<?> getOwnershipHistoryByOwner(Integer ownerId) {
        try {
            List<OwnershipHistory> history = ownershipHistoryRepository.findByOwnerIdOrderByStartDateDesc(Long.valueOf(ownerId));
            List<OwnershipHistoryResponse> response = history.stream()
                    .map(OwnershipHistoryResponse::fromEntity)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.builder()
                            .message("Failed to get owner history: " + e.getMessage())
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }


    private void createOwnershipHistory(Owner owner, Vehicle vehicle, Plate plate) {
        OwnershipHistory history = OwnershipHistory.builder()
                .owner(owner)
                .vehicle(vehicle)
                .plate(plate)
                .startDate(LocalDateTime.now())
                .currentOwnership(true)
                .build();
        ownershipHistoryRepository.save(history);
    }

}