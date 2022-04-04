package com.joel.ecommerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@Column
	private String uuid;
	
	@Column
	private String name;
	
	@Column
	private String street;
	
	@Column
	private String state;
	
	@Column
	private String city;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Customer customer;
}
