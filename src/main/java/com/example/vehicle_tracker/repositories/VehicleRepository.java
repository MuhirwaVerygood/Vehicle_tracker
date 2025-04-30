package com.example.vehicle_tracker.repositories;

import com.example.vehicle_tracker.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository  extends JpaRepository<Vehicle, Integer> {
    Optional<Vehicle> findVehicleByChasisNumber(String chasisNumber);
}
