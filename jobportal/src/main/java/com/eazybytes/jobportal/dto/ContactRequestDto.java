package com.eazybytes.jobportal.dto;


import jakarta.validation.constraints.*;

import java.io.Serializable;

public record ContactRequestDto(
        @NotBlank(message = "Email cannot be Empty")
        @Email(message = "Invalid email address")
        String email,

        @NotBlank(message = "Message cannot be Empty")
        @Size(min = 5,max = 500,message = "Message must be between 5 to 500 characters")
        String message,

        @NotBlank(message = "Name cannot be Empty")
        @Size(min = 3,max = 100,message = "Name must be between 3 to 100 characters")
        String name,

        @NotBlank(message = "Subject cannot be Empty")
        String subject,

        @NotBlank(message = "UserType cannot be Empty")
        @Pattern(regexp = "Job Seeker|Employer|Other",message = "User type must be one of : Job Seeker,Employer,Other")
        String userType) implements Serializable {
}