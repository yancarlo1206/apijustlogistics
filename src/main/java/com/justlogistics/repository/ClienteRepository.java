package com.justlogistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justlogistics.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Optional<Cliente> findByUsername(String username);

}
