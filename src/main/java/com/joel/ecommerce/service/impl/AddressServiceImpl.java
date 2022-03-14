package com.joel.ecommerce.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.joel.ecommerce.domain.Address;
import com.joel.ecommerce.domain.Customer;
import com.joel.ecommerce.exception.EntityNotFoundException;
import com.joel.ecommerce.model.RequestAddressModel;
import com.joel.ecommerce.model.ResponseAddressModel;
import com.joel.ecommerce.repository.AddressRepository;
import com.joel.ecommerce.repository.CustomerRepository;
import com.joel.ecommerce.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private CustomerRepository customerRepository;
	private ModelMapper modelMapper;

	public AddressServiceImpl(AddressRepository addressRepository, CustomerRepository customerRepository,
			ModelMapper modelMapper) {
		this.addressRepository = addressRepository;
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Collection<ResponseAddressModel> getAllByCustomerId(String customerUuid) {
		
		log.info("Getting all addresses by customer id {}...", customerUuid);
		
		Customer customer = customerRepository
				.findByUuid(customerUuid)
				.orElseThrow(EntityNotFoundException::new);
		
		List<ResponseAddressModel> addresses = addressRepository
				.findAllByCustomerId(customer.getId())
				.stream()
				.map(address -> modelMapper.map(address, ResponseAddressModel.class))
				.collect(Collectors.toList());
		
		log.info("{} total addresses obtained", addresses.size());
		
		return addresses;
	}
	
	@Override
	public ResponseAddressModel getById(String uuid) {
		
		log.info("Getting address {} information...", uuid);
		
		Address addressSaved = addressRepository
				.findByUuid(uuid)
				.orElseThrow(EntityNotFoundException::new);
		
		ResponseAddressModel addressResponse = modelMapper.map(addressSaved, ResponseAddressModel.class);
		
		log.info("Address {} information obtained", uuid);
		
		return addressResponse;
	}

	@Override
	public ResponseAddressModel add(String customerUuid, RequestAddressModel addressRequest) {
		
		log.info("Saving address information by customer id {}...", customerUuid);

		Customer customer = customerRepository
				.findByUuid(customerUuid)
				.orElseThrow(EntityNotFoundException::new);
		
		Address addressToSave = modelMapper.map(addressRequest, Address.class);
		
		addressToSave.setUuid(UUID.randomUUID().toString());
		addressToSave.setCustomer(customer);
		
		Address addressSaved = addressRepository.save(addressToSave);
		
		ResponseAddressModel addressReponse = modelMapper.map(addressSaved, ResponseAddressModel.class);
		
		log.info("Address {} saved", addressSaved.getUuid());
		
		return addressReponse;
	}

	@Override
	public ResponseAddressModel update(String uuid, RequestAddressModel addressRequest) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String uuid) {

		log.info("Getting address {} to be deleted...", uuid);
		
		Address addressToDelete = addressRepository
				.findByUuid(uuid)
				.orElseThrow(EntityNotFoundException::new);
		
		addressRepository.delete(addressToDelete);
		
		log.info("Address with {} deleted", uuid);
	}
}
