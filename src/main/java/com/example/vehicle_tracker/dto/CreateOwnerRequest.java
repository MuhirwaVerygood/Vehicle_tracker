package com.example.vehicle_tracker.dto;

import com.example.vehicle_tracker.models.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOwnerRequest {

    @Valid
    private List<PlateRequest> plates;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlateRequest {
        @NotBlank(message = "Plate number is required")
        @Pattern(regexp = "^[A-Z0-9]{1,10}$", message = "Invalid plate number format")
        private String plateNumber;

        @NotNull(message = "Issued date is required")
        private Date issuedDate;
    }


    // Owner names: 2-50 chars, only letters/spaces/hyphens, no leading/trailing spaces
    @NotBlank(message = "Owner names are required")
    @Size(min = 2, max = 50, message = "Owner names must be 2-50 characters")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*$",
            message = "Owner names can only contain letters, spaces, or hyphens"
    )
    private String ownerNames;

    // National ID: Exactly 16 digits, starts with 1
    @NotNull(message = "National ID is required")
    @Pattern(
            regexp = "^1\\d{15}$",
            message = "National ID must be 16 digits starting with 1"
    )
    private String nationalId; // Changed to String for precise validation

    // Phone number: Starts with 078/079 followed by 7 digits (10 total)
    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(078|079)\\d{7}$",
            message = "Phone must start with 078 or 079 followed by 7 digits"
    )
    private String phoneNumber;

    @Valid
    @NotNull(message = "Address is required")
    private AddressRequest address;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddressRequest {

        // Country: 2-50 chars, no numbers/special chars
        @NotBlank(message = "Country is required")
        @Size(min = 2, max = 50, message = "Country must be 2-50 characters")
        @Pattern(
                regexp = "^[A-Za-z\\s-]+$",
                message = "Country can only contain letters and hyphens"
        )
        private String country;

        // Province: Same as country
        @NotBlank(message = "Province is required")
        @Size(min = 2, max = 50, message = "Province must be 2-50 characters")
        @Pattern(
                regexp = "^[A-Za-z\\s-]+$",
                message = "Province can only contain letters and hyphens"
        )
        private String province;

        // District: Same as country
        @NotBlank(message = "District is required")
        @Size(min = 2, max = 50, message = "District must be 2-50 characters")
        @Pattern(
                regexp = "^[A-Za-z\\s-]+$",
                message = "District can only contain letters and hyphens"
        )
        private String district;

        // Sector: Same as country
        @NotBlank(message = "Sector is required")
        @Size(min = 2, max = 50, message = "Sector must be 2-50 characters")
        @Pattern(
                regexp = "^[A-Za-z\\s-]+$",
                message = "Sector can only contain letters and hyphens"
        )
        private String sector;

        // Cell: Same as country
        @NotBlank(message = "Cell is required")
        @Size(min = 2, max = 50, message = "Cell must be 2-50 characters")
        @Pattern(
                regexp = "^[A-Za-z\\s-]+$",
                message = "Cell can only contain letters and hyphens"
        )
        private String cell;

        // Village: Same as country
        @NotBlank(message = "Village is required")
        @Size(min = 2, max = 50, message = "Village must be 2-50 characters")
        @Pattern(
                regexp = "^[A-Za-z\\s-]+$",
                message = "Village can only contain letters and hyphens"
        )
        private String village;

        public Address toAddress() {
            return Address.builder()
                    .country(country.trim())
                    .province(province.trim())
                    .district(district.trim())
                    .sector(sector.trim())
                    .cell(cell.trim())
                    .village(village.trim())
                    .build();
        }



    }
}

