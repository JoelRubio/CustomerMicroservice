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

import com.joel.ecommerce.domain.Customer;
import com.joel.ecommerce.model.RequestCustomerModel;
import com.joel.ecommerce.model.ResponseCustomerModel;
import com.joel.ecommerce.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private ModelMapper modelMapper;

	
	@Test
	public void getCustomerById() {
		
		//given
		String uuid 						   = UUID.randomUUID().toString();
		Optional<Customer> customerSaved       = Optional.of(new Customer());
		ResponseCustomerModel expectedResponse = new ResponseCustomerModel();
		ResponseCustomerModel actualResponse;
		
		expectedResponse.setUuid(uuid);
		expectedResponse.setName("John Doe");
		expectedResponse.setEmail("john@email.com");
		
		//when
		when(customerRepository.findByUuid(uuid)).thenReturn(customerSaved);
		when(modelMapper.map(customerSaved.get(), ResponseCustomerModel.class)).thenReturn(expectedResponse);
		
		actualResponse = customerService.getById(uuid);
		
		//then
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}
	
	@Test
	public void addNewCustomer() {
		
		//given
		RequestCustomerModel customerRequest   = new RequestCustomerModel();
		Customer customerToSave                = new Customer();
		Customer customerSaved                 = new Customer();
		ResponseCustomerModel expectedResponse = new ResponseCustomerModel();
		ResponseCustomerModel actualResponse; 
		
		expectedResponse.setUuid("fake uuid");
		expectedResponse.setName("fake name");
		expectedResponse.setEmail("fake email");
		
		//when
		when(modelMapper.map(customerRequest, Customer.class)).thenReturn(customerToSave);
		when(customerRepository.save(customerToSave)).thenReturn(customerSaved);
		when(modelMapper.map(customerSaved, ResponseCustomerModel.class)).thenReturn(expectedResponse);
		
		actualResponse = customerService.add(customerRequest);
		
		//then
		assertThat(expectedResponse).isEqualTo(actualResponse);
	}
	
	@Test
	public void deleteCustomerById() {
		
		//given
		String uuid                      = UUID.randomUUID().toString();
		Optional<Customer> customerSaved = Optional.of(new Customer());
		
		//when
		when(customerRepository.findByUuid(uuid)).thenReturn(customerSaved);
		doNothing().when(customerRepository).delete(customerSaved.get());
		
		customerService.delete(uuid);
		
		//then
		verify(customerRepository, times(1)).findByUuid(uuid);
		verify(customerRepository, times(1)).delete(customerSaved.get());
	}
	
	@Test
	public void expectExceptionWhenIdIsNotFound() {
		
		//given
		String emptyUuid = "";
		
		//when
		when(customerRepository.findByUuid(emptyUuid)).thenThrow(RuntimeException.class);
		
		//then
		assertThrows(RuntimeException.class, () -> customerService.getById(emptyUuid));
	}
}