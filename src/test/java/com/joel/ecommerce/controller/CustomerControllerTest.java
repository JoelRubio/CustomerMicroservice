package com.joel.ecommerce.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.ecommerce.model.RequestCustomerModel;
import com.joel.ecommerce.model.ResponseCustomerModel;
import com.joel.ecommerce.service.CustomerService;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;

	@Test
	public void getAllCustomers() throws Exception {
		
		//given
		final String URI = "/api/v1/customers";
		ResponseCustomerModel response1 = new ResponseCustomerModel("123", "name1", "email1", List.of());
		ResponseCustomerModel response2 = new ResponseCustomerModel("1234", "name2", "email2", List.of());
		Collection<ResponseCustomerModel > customers = Arrays.asList(response1, response2);
		
		//when
		when(customerService.getAll()).thenReturn(customers);
		
		//then
		this.mockMvc
			.perform(get(URI)
						.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].uuid", is("123")))
			.andExpect(jsonPath("$[0].name", is("name1")))
			.andExpect(jsonPath("$[0].email", is("email1")))
			.andExpect(jsonPath("$[1].uuid", is("1234")))
			.andExpect(jsonPath("$[1].name", is("name2")))
			.andExpect(jsonPath("$[1].email", is("email2")));
	}
	
	@Test
	public void getCustomerById() throws Exception  {
		
		//given
		final String URI = "/api/v1/customers/{customer_uuid}";
		ResponseCustomerModel expectedResponse = new ResponseCustomerModel();
		String uuid  = UUID.randomUUID().toString();
		String name  = "fake name";
		String email = "fake email";
		
		expectedResponse.setUuid(uuid);
		expectedResponse.setName(name);
		expectedResponse.setEmail(email);
		
		//when
		when(customerService.getById(uuid)).thenReturn(expectedResponse);
		
		//then
		this.mockMvc
			.perform(get(URI, uuid)
						.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.uuid", is(uuid)))
			.andExpect(jsonPath("$.name", is(name)))
			.andExpect(jsonPath("$.email", is(email)));
	}
	
	@Test
	public void addNewCustomer() throws Exception {
		
		//given
		final String URI = "/api/v1/customers";
		RequestCustomerModel customerRequest   = new RequestCustomerModel();
		ResponseCustomerModel expectedResponse = new ResponseCustomerModel();
		String name     = "fake name";
		String email    = "fake email";
		String password = "fake password";
		
		customerRequest.setName(name);
		customerRequest.setEmail(email);
		customerRequest.setPassword(password);
		
		expectedResponse.setName(name);
		expectedResponse.setEmail(email);
		
		//when
		when(customerService.add(customerRequest)).thenReturn(expectedResponse);
		
		
		//then
		this.mockMvc
			.perform(post(URI)
						.content(new ObjectMapper().writeValueAsString(customerRequest))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
//			.andExpect(jsonPath("$.name", is(name)))
//			.andExpect(jsonPath("$.email", is(email)));
	}
	
	@Test
	public void deleteCustomerById() throws Exception {
		
		//Given
		final String URI = "/api/v1/customers/{customer_uuid}";
		String uuid      = UUID.randomUUID().toString();
		
		//when
		doNothing().when(customerService).delete(uuid);
		
		//then
		this.mockMvc
			.perform(delete(URI, uuid))
			.andExpect(status().isNoContent());
	}
}




