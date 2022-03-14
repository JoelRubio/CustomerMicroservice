package com.joel.ecommerce.model;

import java.io.Serializable;
import java.util.List;

import com.joel.ecommerce.domain.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCustomerModel implements Serializable {

	private static final long serialVersionUID = -1876829007765044296L;

	private String uuid;
	private String name;
	private String email;
	private List<Address> addresses;
}
