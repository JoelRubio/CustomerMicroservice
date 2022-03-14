package com.joel.ecommerce.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAddressModel implements Serializable {

	private static final long serialVersionUID = 6216676440006993350L;

	private String uuid;
	private String name;
	private String street;
	private String state;
	private String city;
}
