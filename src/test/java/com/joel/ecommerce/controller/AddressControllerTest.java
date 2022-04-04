package com.joel.ecommerce.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.ecommerce.model.RequestAddressModel;
import com.joel.ecommerce.model.ResponseAddressModel;
import com.joel.ecommerce.service.AddressService;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AddressService addressService;
	
	
	@Test
	public void getAddressById() throws Exception {
		
		//given
		final String URI    = "/api/v1/customers/{customer_uuid}/addresses/{address_uuid}";
		String customerUuid = UUID.randomUUID().toString();
		String addressUuid  = UUID.randomUUID().toString();
		ResponseAddressModel expectedResponse = new ResponseAddressModel();
		String name   = "fake name";
		String street = "fake street";
		String state  = "fake state";
		String city   = "fake city";
		
		expectedResponse.setUuid(addressUuid);
		expectedResponse.setName(name);
		expectedResponse.setStreet(street);
		expectedResponse.setState(state);
		expectedResponse.setCity(city);
		
		//when
		when(addressService.getById(addressUuid)).thenReturn(expectedResponse);
		
		//then
		this.mockMvc
			.perform(get(URI, customerUuid, addressUuid)
						.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.uuid", is(addressUuid)))
			.andExpect(jsonPath("$.name", is(name)))
			.andExpect(jsonPath("$.street", is(street)))
			.andExpect(jsonPath("$.state", is(state)))
			.andExpect(jsonPath("$.city", is(city)));
	}
	
	@Test
	public void addNewAddressByCustomerId() throws Exception {
		
		//given
		final String URI    = "/api/v1/customers/{customer_uuid}/addresses";
		String customerUuid = UUID.randomUUID().toString();
		RequestAddressModel addressRequest    = new RequestAddressModel();
		ResponseAddressModel expectedResponse = new ResponseAddressModel();
		String name   = "fake name";
		String street = "fake street";
		String state  = "fake state";
		String city   = "fake city";
		
		addressRequest.setName(name);
		addressRequest.setStreet(street);
		addressRequest.setState(state);
		addressRequest.setCity(city);
		
		expectedResponse.setUuid("12345");
		expectedResponse.setName(name);
		expectedResponse.setStreet(street);
		expectedResponse.setState(state);
		expectedResponse.setCity(city);
		
		//when
		when(addressService.add(customerUuid, addressRequest)).thenReturn(expectedResponse);
		
		//then
		this.mockMvc
			.perform(post(URI, customerUuid)
						.content(new ObjectMapper().writeValueAsString(addressRequest))
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void deleteAddressById() throws Exception {
		
		//given
		final String URI    = "/api/v1/customers/{customer_uuid}/addresses/{address_uuid}";
		String customerUuid = UUID.randomUUID().toString();
		String addressUuid  = UUID.randomUUID().toString();
		
		//when
		doNothing().when(addressService).delete(addressUuid);
		
		//then
		this.mockMvc
			.perform(delete(URI, customerUuid, addressUuid))
			.andExpect(status().isNoContent());
	}
}