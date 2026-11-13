package com.hasan.DTO;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private LocalDate dob;

    private String gender;

    private String aadhaarNumber;

    private String panNumber;

    private String occupation;

    private BigDecimal annualIncome;

    private String customerStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}