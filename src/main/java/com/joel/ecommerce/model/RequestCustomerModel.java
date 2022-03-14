package com.joel.ecommerce.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCustomerModel implements Serializable {

	private static final long serialVersionUID = 5326171229869199691L;

	private String name;
	private String email;
	private String password;
}
