package com.sagor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
