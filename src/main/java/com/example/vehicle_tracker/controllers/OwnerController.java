package com.example.vehicle_tracker.controllers;
import com.example.vehicle_tracker.dto.CreateOwnerRequest;
import com.example.vehicle_tracker.dto.TransferOwnershipRequest;
import com.example.vehicle_tracker.models.Owner;
import com.example.vehicle_tracker.services.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owners")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        return ownerService.createOwner(request);
    }



    @PostMapping("/transfer")
    public ResponseEntity<?> transferOwnership(@RequestBody TransferOwnershipRequest request) {
        return ownerService.transferOwnership(request);
    }

    @GetMapping("/history/{vehicleId}")
    public ResponseEntity<?> getOwnershipHistory(@PathVariable Long vehicleId) {
        return ownerService.getOwnershipHistory(vehicleId);
    }

    @GetMapping("/current/{vehicleId}")
    public ResponseEntity<?> getCurrentOwner(@PathVariable Long vehicleId) {
        return ownerService.getCurrentOwner(vehicleId);
    }

    @GetMapping("/owner-history/{ownerId}")
    public ResponseEntity<?> getOwnershipHistoryByOwner(@PathVariable Integer ownerId) {
        return ownerService.getOwnershipHistoryByOwner(ownerId);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> authenticate(){
        return ResponseEntity.ok("Hello world");
    }

}