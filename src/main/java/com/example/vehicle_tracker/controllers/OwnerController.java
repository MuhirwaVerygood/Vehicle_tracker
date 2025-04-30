package com.example.vehicle_tracker.controllers;
import com.example.vehicle_tracker.dto.CreateOwnerRequest;
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
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        return ownerService.createOwner(request);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> authenticate(){
        return ResponseEntity.ok("Hello world");
    }

}