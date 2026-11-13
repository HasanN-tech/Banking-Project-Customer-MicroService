package com.hasan.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hasan.DTO.CustomerRequest;
import com.hasan.DTO.CustomerResponse;
import com.hasan.entity.Customer;
import com.hasan.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/add")
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody Customer request) {

		CustomerResponse response = customerService.createCustomer(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// GET BY ID
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {

		return ResponseEntity.ok(customerService.getCustomer(id));
	}

	// GET ALL
	@GetMapping("/page")
	public ResponseEntity<Page<CustomerResponse>> getCustomers(

			@RequestParam(defaultValue = "0") int page,

			@RequestParam(defaultValue = "10") int size,

			@RequestParam(defaultValue = "customerId") String sortBy,

			@RequestParam(defaultValue = "ASC") String direction) {

		return ResponseEntity.ok(customerService.getCustomers(page, size, sortBy, direction));
	}

	// UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
			@Valid @RequestBody CustomerRequest request) {

		return ResponseEntity.ok(customerService.updateCustomer(id, request));
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {

		customerService.deleteCustomer(id);

		return ResponseEntity.ok("Customer deleted successfully");
	}

	@GetMapping("/search/status")
	public ResponseEntity<Page<CustomerResponse>> searchByStatus(

			@RequestParam String status,

			@RequestParam(defaultValue = "0") int page,

			@RequestParam(defaultValue = "10") int size) {

		return ResponseEntity.ok(customerService.searchByStatus(status, page, size));
	}
}