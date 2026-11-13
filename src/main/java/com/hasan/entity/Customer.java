package com.hasan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BankCustomer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@NotBlank(message = "First Name is required")
	private String firstName;

	@NotBlank(message = "Last Name is required")
	private String lastName;

	@Email(message = "Invalid Email")
	@NotBlank(message = "Email is required")
	private String email;

	@NonNull
	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid Mobile Number")
	private String mobile;

	@NonNull
	@Pattern(regexp = "^[0-9]{12}$", message = "Invalid Aadhaar Number")
	private String aadhaarNumber;

	@NonNull
	@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN Number")
	private String panNumber;

	@NonNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dob;

	@NonNull
	private String gender;

	@NonNull
	private String occupation;

	@NonNull
	private BigDecimal annualIncome;

	private String customerStatus;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}