package com.joel.ecommerce.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.ecommerce.model.RequestCustomerModel;
import com.joel.ecommerce.model.ResponseCustomerModel;
import com.joel.ecommerce.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private CustomerService service;
	
	public CustomerController(CustomerService service) {
		
		this.service = service;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ResponseCustomerModel>> getAll() {
		
		Collection<ResponseCustomerModel> customers = service.getAll();
		
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping(path = "/{uuid}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCustomerModel> getById(@PathVariable String uuid) {
		
		ResponseCustomerModel customerResponse = service.getById(uuid); 
		
		return ResponseEntity.ok(customerResponse);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCustomerModel> add(@RequestBody RequestCustomerModel customerRequest) {
		
		//validate customer
		
		ResponseCustomerModel customerResponse = service.add(customerRequest);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(customerResponse);
	}
	
	@PutMapping(path = "/{uuid}",
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable String uuid, @RequestBody RequestCustomerModel customerRequest) {
		
		//validate customer
		
		service.update(uuid, customerRequest);
		
		return ResponseEntity
				.ok()
				.build();
	}
	
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> delete(@PathVariable String uuid) {
		
		service.delete(uuid);
		
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
