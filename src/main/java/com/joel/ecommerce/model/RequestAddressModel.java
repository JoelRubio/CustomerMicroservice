package com.joel.ecommerce.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestAddressModel implements Serializable {

	private static final long serialVersionUID = -5771280802173153444L;
	
	private String name;
	private String street;
	private String state;
	private String city;
}
