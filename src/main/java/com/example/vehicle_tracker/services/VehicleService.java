package com.example.vehicle_tracker.services;

import com.example.vehicle_tracker.dto.ErrorResponse;
import com.example.vehicle_tracker.dto.VehiclePriceHistoryResponse;
import com.example.vehicle_tracker.dto.VehicleRequest;
import com.example.vehicle_tracker.models.OwnershipHistory;
import com.example.vehicle_tracker.models.Vehicle;
import com.example.vehicle_tracker.repositories.OwnerRepository;
import com.example.vehicle_tracker.repositories.OwnershipHistoryRepository;
import com.example.vehicle_tracker.repositories.PlateRepository;
import com.example.vehicle_tracker.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final OwnershipHistoryRepository ownershipHistoryRepository;

    public ResponseEntity<?> createVehicle(VehicleRequest request){

        Optional<Vehicle> vehicleExists = vehicleRepository.findVehicleByChasisNumber(request.getChasisNumber());
        if(vehicleExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponse
                            .builder()
                            .message("Vehicle with that chasis number exists")
                            .statusCode(409)
                            .build()
            );
        }


        Vehicle vehicle = Vehicle
                .builder()
                .chasisNumber(request.getChasisNumber())
                .manufacturer(request.getManufacturer())
                .price(request.getPrice())
                .year(request.getYear())
                .build();

        var savedVehicle =  vehicleRepository.save(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }




    public ResponseEntity<?> getVehiclePriceHistory( Integer vehicleId) {
        List<OwnershipHistory> history = ownershipHistoryRepository.findByVehicleIdOrderByStartDateDesc(Long.valueOf(vehicleId));

        List<VehiclePriceHistoryResponse> response = history.stream()
                .filter(h -> h.getTransferPrice() != null)
                .map(h -> VehiclePriceHistoryResponse.builder()
                        .date(h.getStartDate())
                        .price(h.getTransferPrice())
                        .ownerName(h.getOwner().getOwnerNames())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
