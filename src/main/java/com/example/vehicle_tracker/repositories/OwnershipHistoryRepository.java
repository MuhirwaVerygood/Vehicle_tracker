package com.example.vehicle_tracker.repositories;

import com.example.vehicle_tracker.models.OwnershipHistory;
import com.example.vehicle_tracker.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnershipHistoryRepository extends JpaRepository<OwnershipHistory, Long> {

    // Explicit JPQL queries to avoid derivation issues
    @Query("SELECT oh FROM OwnershipHistory oh WHERE oh.vehicle.id = :vehicleId AND oh.currentOwnership = :current")
    Optional<OwnershipHistory> findByVehicleIdAndCurrentOwnership(
            @Param("vehicleId") Long vehicleId,
            @Param("current") boolean current);

    @Query("SELECT oh FROM OwnershipHistory oh WHERE oh.vehicle.id = :vehicleId ORDER BY oh.startDate DESC")
    List<OwnershipHistory> findByVehicleIdOrderByStartDateDesc(@Param("vehicleId") Long vehicleId);

    @Query("SELECT oh FROM OwnershipHistory oh WHERE oh.owner.id = :ownerId ORDER BY oh.startDate DESC")
    List<OwnershipHistory> findByOwnerIdOrderByStartDateDesc(@Param("ownerId") Long ownerId);

    // Query method for vehicle instance
    Optional<OwnershipHistory> findByVehicleAndCurrentOwnership(Vehicle vehicle, boolean current);

    // Consolidated full history method (same as findByVehicleIdOrderByStartDateDesc)
    default List<OwnershipHistory> findFullHistoryByVehicleId(Long vehicleId) {
        return findByVehicleIdOrderByStartDateDesc(vehicleId);
    }

}