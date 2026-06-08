package com.eazybytes.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank
        @Size(min = 5, max = 30, message = "The length of the name should be between 5 to 100 characters")
        String name,

        @NotBlank(message = "Email is requried")
        @Email(message = "Email address must be valid vlaue")
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{10}", message = "Mobile number must be exactly 10 digits")
        String mobileNumber,

        @NotBlank(message = "Password is requried")
        @Size(min = 8, max = 20, message = "Password length must be between 8 and 50 characters")
        String password
) {
}