package com.joel.ecommerce.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.ecommerce.model.RequestAddressModel;
import com.joel.ecommerce.model.ResponseAddressModel;
import com.joel.ecommerce.service.AddressService;

@RestController
@RequestMapping("/api/v1/customers/{customer_uuid}/addresses")
public class AddressController {

	private AddressService service;
	
	public AddressController(AddressService service) {
		this.service = service;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ResponseAddressModel>> getAllByCustomerId(@PathVariable("customer_uuid") String customerUuid) {
		
		Collection<ResponseAddressModel> addresses = service.getAllByCustomerId(customerUuid);
		
		return ResponseEntity.ok(addresses);
	}
	
	@GetMapping(path = "/{address_uuid}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAddressModel> getById(@PathVariable("address_uuid") String uuid) {
		
		ResponseAddressModel addressResponse = service.getById(uuid);
		
		return ResponseEntity.ok(addressResponse);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAddressModel> add(@PathVariable("customer_uuid") String customerUuid,
								    				@RequestBody RequestAddressModel addressRequest) {
		
		ResponseAddressModel addressResponse = service.add(customerUuid, addressRequest);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(addressResponse);
	}
	
	@DeleteMapping("/{address_uuid}")
	public ResponseEntity<Void> delete(@PathVariable("address_uuid") String uuid) {
		
		service.delete(uuid);
		
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
