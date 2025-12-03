package com.justlogistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.justlogistics.entity.LineaTiempo;


public interface LineaTiempoRepository extends JpaRepository<LineaTiempo, Integer> {
	
	List<LineaTiempo> findByProcesoId(Integer procesoId);

}
