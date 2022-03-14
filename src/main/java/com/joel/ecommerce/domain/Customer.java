package com.joel.ecommerce.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String uuid;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column(name = "encrypted_password")
	private String encryptedPassword;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Address> addresses;
	
	public void addAddress(Address address) {
		
		addresses.add(address);
		address.setCustomer(this);
	}
	
	public void removeAddress(Address address) {
		
		addresses.remove(address);
		address.setCustomer(null);
	}
}
