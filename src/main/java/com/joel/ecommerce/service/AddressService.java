package com.joel.ecommerce.service;

import java.util.Collection;

import com.joel.ecommerce.model.RequestAddressModel;
import com.joel.ecommerce.model.ResponseAddressModel;

public interface AddressService {

	Collection<ResponseAddressModel> getAllByCustomerId(String customerUuid);
	ResponseAddressModel getById(String uuid);
	ResponseAddressModel add(String customerUuid, RequestAddressModel addressRequest);
	ResponseAddressModel update(String uuid, RequestAddressModel addressRequest);
	void delete(String uuid);
}