package com.example.vehicle_tracker.controllers;

import com.example.vehicle_tracker.dto.PlateRequest;
import com.example.vehicle_tracker.services.PlateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plates")
@RequiredArgsConstructor

@PreAuthorize("hasRole('ADMIN')")
public class PlateController {
    private final PlateService plateService;

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping    ("/new")
    public ResponseEntity<?> addPlate(@RequestBody PlateRequest plateRequest) {
        return plateService.addPlate(plateRequest);
    }
}
