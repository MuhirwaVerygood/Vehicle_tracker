package com.example.vehicle_tracker.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ownership_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnershipHistory {
    @Id
    @SequenceGenerator(name = "ownership-seq" , sequenceName = "ownership-seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner_id" , referencedColumnName = "id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicleId")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "plate_id", referencedColumnName = "plateId")
    private Plate plate;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(name = "is_current")
    private boolean currentOwnership;
}