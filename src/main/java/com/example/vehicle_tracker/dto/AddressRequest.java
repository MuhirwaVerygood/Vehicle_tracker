package com.example.vehicle_tracker.dto;
import com.example.vehicle_tracker.models.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
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