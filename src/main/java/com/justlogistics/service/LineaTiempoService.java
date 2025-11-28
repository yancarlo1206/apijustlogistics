package com.justlogistics.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.LineaTiempo;
import com.justlogistics.entity.Proceso;
import com.justlogistics.repository.LineaTiempoRepository;
import com.justlogistics.repository.ProcesoRepository;

@Service
public class LineaTiempoService {

	@Autowired
	private LineaTiempoRepository lineaRepository;
	
	@Autowired
	private ProcesoRepository procesoRepository;

	public LineaTiempo guardar(LineaTiempo request) {
		
		Proceso proceso = procesoRepository.findById(request.getProceso().getId()).orElseThrow(
				() -> new RuntimeException("Proceso no encontradado con ID: " + request.getProceso().getId()));
		
		request.setProceso(proceso);
		request.setFechacreacion(LocalDateTime.now());
		request.setFechaactualizacion(LocalDateTime.now());
		return lineaRepository.save(request);
	}

	public List<LineaTiempo> listar() {
		return lineaRepository.findAll();
	}

	public LineaTiempo get(Integer id) {
		return lineaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Liena de tiempo no encontrada con ID: " + id));
	}

	public LineaTiempo actualizar(Integer id, LineaTiempo request) {
		
		
		LineaTiempo camposPro = lineaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Liena de tiempo no encontrada con ID: " + id));
		
		Proceso proceso = procesoRepository.findById(request.getProceso().getId()).orElseThrow(
				() -> new RuntimeException("Proceso no encontradao con ID: " + request.getProceso().getId()));
		
		
		camposPro.setProceso(proceso);
		camposPro.setEstado(request.getEstado());
		camposPro.setUbicacion(request.getUbicacion());
		camposPro.setDescripcion(request.getDescripcion());
		camposPro.setFechaactualizacion(LocalDateTime.now());
		return lineaRepository.save(camposPro);
	}

	public boolean eliminar(Integer id) {
		if (lineaRepository.existsById(id)) {
			lineaRepository.deleteById(id);
			return true;
		}
		return false;
	}

}

