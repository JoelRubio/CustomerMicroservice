package com.joel.ecommerce.service;

import java.util.Collection;

import com.joel.ecommerce.model.RequestCustomerModel;
import com.joel.ecommerce.model.ResponseCustomerModel;

public interface CustomerService {

	Collection<ResponseCustomerModel> getAll();
	ResponseCustomerModel getById(String uuid);
	ResponseCustomerModel add(RequestCustomerModel customer);
	ResponseCustomerModel update(String uuid, RequestCustomerModel customer);
	void delete(String uuid);
}
