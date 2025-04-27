package com.example.vehicle_tracker.repositories;

import com.example.vehicle_tracker.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner , Integer> {
}
