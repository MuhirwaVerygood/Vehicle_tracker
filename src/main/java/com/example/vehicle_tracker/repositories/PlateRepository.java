package com.example.vehicle_tracker.repositories;

import com.example.vehicle_tracker.models.Plate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlateRepository extends JpaRepository<Plate, Integer> {
    Optional<Plate> findPlateByPlateNumber(String plateNumber);
}
