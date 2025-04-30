package com.example.vehicle_tracker.controllers;

import com.example.vehicle_tracker.dto.VehicleRequest;
import com.example.vehicle_tracker.models.Vehicle;
import com.example.vehicle_tracker.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")

@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class VehicleController {
    private final VehicleService vehicleService;


    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/new")
    public ResponseEntity<?> createVehicle(@RequestBody VehicleRequest vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }



}
