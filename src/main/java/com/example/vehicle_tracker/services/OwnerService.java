package com.example.vehicle_tracker.services;
import com.example.vehicle_tracker.dto.CreateOwnerRequest;
import com.example.vehicle_tracker.models.Owner;
import com.example.vehicle_tracker.models.Plate;
import com.example.vehicle_tracker.repositories.OwnerRepository;
import com.example.vehicle_tracker.repositories.PlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PlateRepository plateRepository;

    // @Transactional
    public Owner createOwner(CreateOwnerRequest request) {
        // Convert request to Owner entity
        Owner owner = Owner.builder()
                .ownerNames(request.getOwnerNames())
                .nationalId(request.getNationalId())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress().toAddress())
                .build();



        var savedOwner = ownerRepository.save(owner);

        if(request.getPlates() != null && !request.getPlates().isEmpty()) {
            List<Plate> plates = request.getPlates().stream()
                    .map(plateReq -> Plate.builder()
                            .plateNumber(plateReq.getPlateNumber())
                            .issuedDate(plateReq.getIssuedDate())
                            .owner(savedOwner)
                            .build())
                    .collect(Collectors.toList());

            plateRepository.saveAll(plates);
        }

        // Save the owner
        return savedOwner;
    }
}