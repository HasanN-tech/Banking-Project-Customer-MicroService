package com.hasan.DTO;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class CustomerRequest {

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Invalid Mobile Number")
    private String mobile;

    @Pattern(
            regexp = "^[0-9]{12}$",
            message = "Invalid Aadhaar Number")
    private String aadhaarNumber;

    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$",
            message = "Invalid PAN Number")
    private String panNumber;
}