package com.justlogistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.justlogistics.entity.Proceso;


public interface ProcesoRepository extends JpaRepository<Proceso, Integer> {
	
	List<Proceso> findByClienteId(Integer clienteId);

}
