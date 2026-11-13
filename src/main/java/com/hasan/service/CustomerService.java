package com.hasan.service;

import org.springframework.data.domain.Page;

import com.hasan.DTO.CustomerRequest;
import com.hasan.DTO.CustomerResponse;
import com.hasan.entity.Customer;

public interface CustomerService {

	CustomerResponse createCustomer(Customer request);

	CustomerResponse getCustomer(Long customerId);

	Page<CustomerResponse> getCustomers(int page, int size, String sortBy, String direction);

	CustomerResponse updateCustomer(Long customerId, CustomerRequest request);

	void deleteCustomer(Long customerId);

	Page<CustomerResponse> searchByStatus(String status, int page, int size);
}