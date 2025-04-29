package com.example.vehicle_tracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // First Name: Must be 2-50 chars, no leading/trailing spaces, no numbers/symbols
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be 2-50 characters")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*$",
            message = "First name must contain only letters, spaces, or hyphens"
    )
    private String firstName;

    // Last Name: Same as first name
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be 2-50 characters")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*$",
            message = "Last name must contain only letters, spaces, or hyphens"
    )
    private String lastName;

    // Phone Number: Must start with 078 or 079, followed by 7 digits (10 digits total)
    @NotNull(message = "Phone number is required")
    @Pattern(
            regexp = "^(078|079)\\d{7}$",
            message = "Phone number must start with 078 or 079 followed by 7 digits (e.g., 0781234567)"
    )
    private String phoneNumber; // Changed from Integer to String for regex validation

    // National ID: Must be 16 digits, start with 1, followed by birth year (e.g., 12005...)
    @NotNull(message = "National ID is required")
    @Pattern(
            regexp = "^1\\d{15}$", // Starts with 1, followed by 15 digits (16 total)
            message = "National ID must be 16 digits starting with 1"
    )
    private String nationalId;

    // Email: Strict validation (no spaces, valid format)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid (e.g., user@example.com)")
    @Pattern(
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid email format"
    )
    private String email;

    // Password: At least 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least 1 uppercase, 1 lowercase, 1 number, and 1 special character"
    )
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}


