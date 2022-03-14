package com.joel.ecommerce.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.joel.ecommerce.domain.Address;
import com.joel.ecommerce.domain.Customer;
import com.joel.ecommerce.model.RequestAddressModel;
import com.joel.ecommerce.model.ResponseAddressModel;
import com.joel.ecommerce.repository.AddressRepository;
import com.joel.ecommerce.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

	@InjectMocks
	private AddressServiceImpl addressService;
	
	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	
	@Test
	public void getAddressById() {
		
		//given
		String customerUuid  				  = UUID.randomUUID().toString();
		Optional<Address> address             = Optional.of(new Address());
		ResponseAddressModel expectedResponse = new ResponseAddressModel();
		ResponseAddressModel actualResponse;
		
		expectedResponse.setName("Fake address");
		expectedResponse.setStreet("Fake street");
		expectedResponse.setState("Fake state");
		expectedResponse.setCity("Fake city");
		
		//when
		when(addressRepository.findByUuid(customerUuid)).thenReturn(address);
		when(modelMapper.map(address.get(), ResponseAddressModel.class)).thenReturn(expectedResponse);
		
		actualResponse = addressService.getById(customerUuid);
		
		//then
		assertThat(expectedResponse).isEqualTo(actualResponse);
	}
	
	@Test
	public void addNewAddressByCustomerId() {
		
		//given
		String customerUuid                   = UUID.randomUUID().toString();
		Optional<Customer> customer           = Optional.of(new Customer());
		Address address                       = new Address();
		RequestAddressModel addressRequest    = new RequestAddressModel();
		ResponseAddressModel expectedResponse = new ResponseAddressModel();
		ResponseAddressModel actualResponse;
		
		expectedResponse.setName("fake address");
		expectedResponse.setStreet("fake street");
		expectedResponse.setState("fake state");
		expectedResponse.setCity("fake city");
		
		//when
		when(customerRepository.findByUuid(customerUuid)).thenReturn(customer);
		when(modelMapper.map(addressRequest, Address.class)).thenReturn(address);
		when(addressRepository.save(address)).thenReturn(address);
		when(modelMapper.map(address, ResponseAddressModel.class)).thenReturn(expectedResponse);
		
		actualResponse = addressService.add(customerUuid, addressRequest);
		
		//then
		assertThat(expectedResponse).isEqualTo(actualResponse);
	}
	
	@Test
	public void deleteAddressById() {
		
		//given
		String uuid               = UUID.randomUUID().toString();
		Optional<Address> address = Optional.of(new Address());
		
		//when
		when(addressRepository.findByUuid(uuid)).thenReturn(address);
		doNothing().when(addressRepository).delete(address.get());
		
		addressService.delete(uuid);
		
		//then
		verify(addressRepository, times(1)).findByUuid(uuid);
		verify(addressRepository, times(1)).delete(address.get());
	}
	
	@Test
	public void expectExceptionWhenCustomerIdIsNotFound() {
		
		//given
		String emptyCustomerUuid = "";
		RequestAddressModel addressRequest = new RequestAddressModel();
		
		//when
		when(customerRepository.findByUuid(emptyCustomerUuid)).thenThrow(RuntimeException.class);
		
		//then
		assertThrows(RuntimeException.class, () -> addressService.add(emptyCustomerUuid, addressRequest));
	}
	
	@Test
	public void expectExceptionWhenAddressIsNotFound() {
		
		//given
		String emptyAddressUuid = "";
		
		//when
		when(addressRepository.findByUuid(emptyAddressUuid)).thenThrow(RuntimeException.class);
		
		//then
		assertThrows(RuntimeException.class, () -> addressService.getById(emptyAddressUuid));
	}
}