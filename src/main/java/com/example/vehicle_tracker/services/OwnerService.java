package com.example.vehicle_tracker.services;
import com.example.vehicle_tracker.dto.CreateOwnerRequest;
import com.example.vehicle_tracker.models.Owner;
import com.example.vehicle_tracker.models.Plate;
import com.example.vehicle_tracker.repositories.OwnerRepository;
import com.example.vehicle_tracker.repositories.PlateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PlateRepository plateRepository;

     @Transactional
    public ResponseEntity<?> createOwner(CreateOwnerRequest request) {
         var owner =  Owner
                 .builder()
                 .ownerNames(request.getOwnerNames())
                 .plates(new ArrayList<>())
                 .address(request.getAddress().toAddress())
                 .nationalId(request.getNationalId())
                 .phoneNumber(request.getPhoneNumber())
                 .build();

         var savedOwner =   ownerRepository.save(owner);

        List<Plate> plates =   request.getPlates().stream().map(plateRequest -> {
             Plate plate = Plate.builder()
                     .plateNumber(plateRequest.getPlateNumber())
                     .issuedDate(plateRequest.getIssuedDate())
                     .owner(savedOwner)
                     .build();
             return plate;
         }).collect(Collectors.toList());

        plateRepository.saveAll(plates);
        savedOwner.setPlates(plates);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(savedOwner);
    }

}