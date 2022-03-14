package com.joel.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.ecommerce.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByUuid(String uuid);
	List<Address> findAllByCustomerId(long id);
}
