
package com.hasan.service;

import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hasan.DTO.CustomerRequest;
import com.hasan.DTO.CustomerResponse;
import com.hasan.entity.Customer;
import com.hasan.exception.BusinessException;
import com.hasan.exception.CustomerNotFoundException;
import com.hasan.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

		// CREATE CUSTOMER
		@Override
	@Caching(evict = { @CacheEvict(value = "customerSearch", allEntries = true),
			@CacheEvict(value = "customerStatusSearch", allEntries = true) })
	public CustomerResponse createCustomer(Customer request) {

		if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new BusinessException("Customer already exists with email: " + request.getEmail());
		}

		if (customerRepository.findByPanNumber(request.getPanNumber()).isPresent()) {
			throw new BusinessException("Customer already exists with PAN: " + request.getPanNumber());
		}

		if (request.getAadhaarNumber() == null || !request.getAadhaarNumber().matches("\\d{12}")) {
			throw new BusinessException("Aadhaar number must contain exactly 12 digits");
		}

		Customer customer = new Customer();

		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setMobile(request.getMobile());
		customer.setDob(request.getDob());
		customer.setGender(request.getGender());
		customer.setAadhaarNumber(request.getAadhaarNumber());
		customer.setPanNumber(request.getPanNumber());
		customer.setOccupation(request.getOccupation());
		customer.setAnnualIncome(request.getAnnualIncome());

		customer.setCustomerStatus("ACTIVE");

		LocalDateTime now = LocalDateTime.now();
		customer.setCreatedAt(now);
		customer.setUpdatedAt(now);

		Customer savedCustomer = customerRepository.save(customer);

		return convertToResponse(savedCustomer);
	}

	// GET CUSTOMER BY ID
	// =========================================================

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "customers", key = "#p0", unless = "#result == null")
	public CustomerResponse getCustomer(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

		return convertToResponse(customer);
	}

		// GET ALL CUSTOMERS - PAGINATION AND SORTING
	// =========================================================

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "customers", key = "'all:' + #p0 + ':' + #p1 + ':' + #p2 + ':' + #p3")
	public Page<CustomerResponse> getCustomers(int page, int size, String sortBy, String direction) {

		if (page < 0 || size < 1) {
			throw new BusinessException("Page must be >= 0 and size must be >= 1");
		}

		Sort sort = "DESC".equalsIgnoreCase(direction) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return customerRepository.findAll(pageable).map(this::convertToResponse);
	}

	// =========================================================
	// UPDATE CUSTOMER
	// =========================================================

	@Override
	@Caching(evict = { @CacheEvict(value = "customers", key = "#p0"),
			@CacheEvict(value = "customerSearch", allEntries = true),
			@CacheEvict(value = "customerStatusSearch", allEntries = true) })
	public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

		if (!customer.getEmail().equalsIgnoreCase(request.getEmail())
				&& customerRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new BusinessException("Email already exists: " + request.getEmail());
		}

		if (!customer.getPanNumber().equalsIgnoreCase(request.getPanNumber())
				&& customerRepository.findByPanNumber(request.getPanNumber()).isPresent()) {
			throw new BusinessException("PAN already exists: " + request.getPanNumber());
		}

		if (request.getAadhaarNumber() == null || !request.getAadhaarNumber().matches("\\d{12}")) {
			throw new BusinessException("Aadhaar number must contain exactly 12 digits");
		}

		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setMobile(request.getMobile());
		customer.setAadhaarNumber(request.getAadhaarNumber());
		customer.setPanNumber(request.getPanNumber());
		customer.setUpdatedAt(LocalDateTime.now());

		Customer updatedCustomer = customerRepository.save(customer);

		return convertToResponse(updatedCustomer);
	}

	// =========================================================
	// SOFT DELETE CUSTOMER
	// =========================================================

	@Override
	@Caching(evict = { @CacheEvict(value = "customers", key = "#p0"),
			@CacheEvict(value = "customerSearch", allEntries = true),
			@CacheEvict(value = "customerStatusSearch", allEntries = true) })
	public void deleteCustomer(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

		customer.setCustomerStatus("INACTIVE");
		customer.setUpdatedAt(LocalDateTime.now());

		customerRepository.save(customer);
	}

	// =========================================================
	// SEARCH CUSTOMERS BY STATUS
	// =========================================================

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "customerStatusSearch", key = "#p0 + '-' + #p1 + '-' + #p2")
	public Page<CustomerResponse> searchByStatus(String status, int page, int size) {

		if (page < 0 || size < 1) {
			throw new BusinessException("Page must be >= 0 and size must be >= 1");
		}

		Pageable pageable = PageRequest.of(page, size);

		return customerRepository.findByCustomerStatus(status, pageable).map(this::convertToResponse);
	}

	// =========================================================
	// ENTITY TO RESPONSE DTO
	// =========================================================

	private CustomerResponse convertToResponse(Customer customer) {

		CustomerResponse response = new CustomerResponse();

		response.setCustomerId(customer.getCustomerId());
		response.setFirstName(customer.getFirstName());
		response.setLastName(customer.getLastName());
		response.setEmail(customer.getEmail());
		response.setMobile(customer.getMobile());
		response.setDob(customer.getDob());
		response.setGender(customer.getGender());
		response.setAadhaarNumber(customer.getAadhaarNumber());
		response.setPanNumber(customer.getPanNumber());
		response.setOccupation(customer.getOccupation());
		response.setAnnualIncome(customer.getAnnualIncome());
		response.setCustomerStatus(customer.getCustomerStatus());
		response.setCreatedAt(customer.getCreatedAt());
		response.setUpdatedAt(customer.getUpdatedAt());

		return response;
	}
}
