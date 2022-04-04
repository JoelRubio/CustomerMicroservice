package com.joel.ecommerce.service.impl;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.joel.ecommerce.domain.Customer;
import com.joel.ecommerce.exception.EntityNotFoundException;
import com.joel.ecommerce.model.RequestCustomerModel;
import com.joel.ecommerce.model.ResponseCustomerModel;
import com.joel.ecommerce.repository.CustomerRepository;
import com.joel.ecommerce.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository repository;
	private ModelMapper modelMapper;
	
	public CustomerServiceImpl(CustomerRepository repository, ModelMapper modelMapper) {
		
		this.repository  = repository;
		this.modelMapper = modelMapper; 
	}
	
	@Override
	public Collection<ResponseCustomerModel> getAll() {
		
		log.info("Getting all customers...");
		
		Collection<ResponseCustomerModel> customers = repository
				.findAll()
				.stream()
				.map(customer -> modelMapper.map(customer, ResponseCustomerModel.class))
				.collect(Collectors.toList());
		
		log.info("Total customers obtained: {}", customers.size());
		
		return customers;
	}
	
	@Override
	public ResponseCustomerModel getById(String uuid) {

		log.info("Getting customer {} information...", uuid);
		
		Customer customerSaved = repository
				.findByUuid(uuid)
				.orElseThrow(EntityNotFoundException::new);
		
		ResponseCustomerModel customerResponse =  modelMapper.map(customerSaved, ResponseCustomerModel.class);
		
		log.info("Customer {} information obtained", uuid);
		
		return customerResponse;
	}

	@Override
	public ResponseCustomerModel add(RequestCustomerModel customerRequest) {
		
		log.info("Saving customer information...");
		
		Customer customerToSave = modelMapper.map(customerRequest, Customer.class);
		
		customerToSave.setUuid(UUID.randomUUID().toString());
		customerToSave.setEncryptedPassword(customerRequest.getPassword());
		
		Customer customerSaved = repository.save(customerToSave);
		
		ResponseCustomerModel customerResponse = modelMapper.map(customerSaved, ResponseCustomerModel.class);
		
		log.info("Customer {} information saved", customerToSave.getUuid());
		
		return customerResponse;
	}

	@Override
	public ResponseCustomerModel update(String uuid, RequestCustomerModel customer) {
		// TODO Auto-generated method stub
		
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String uuid) {
		
		log.info("Getting customer {} information to be deleted...", uuid);
		
		Customer customerToDelete = repository
				.findByUuid(uuid)
				.orElseThrow(EntityNotFoundException::new);
		
		repository.delete(customerToDelete);
		
		log.info("Customer {} information deleted", uuid);
	}
}
