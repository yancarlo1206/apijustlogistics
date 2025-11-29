package com.justlogistics.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.LineaTiempo;
import com.justlogistics.entity.Proceso;
import com.justlogistics.entity.Estado;
import com.justlogistics.repository.EstadoRepository;
import com.justlogistics.repository.LineaTiempoRepository;
import com.justlogistics.repository.ProcesoRepository;

import jakarta.transaction.Transactional;

@Service
public class LineaTiempoService {

	@Autowired
	private LineaTiempoRepository lineaRepository;

	@Autowired
	private ProcesoRepository procesoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public LineaTiempo guardar(LineaTiempo request) {

		Proceso proceso = procesoRepository.findById(request.getProceso().getId()).orElseThrow(
				() -> new RuntimeException("Proceso no encontrado con ID: " + request.getProceso().getId()));

		Estado estado = estadoRepository.findById(request.getEstado().getId())
				.orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + request.getEstado().getId()));

		// Actualizar el estado en proceso
		proceso.setEstado(estado);
		proceso.setFechaactualizacion(LocalDateTime.now());
		procesoRepository.save(proceso);

		// Guardar linea de tiempo
		request.setProceso(proceso);
		request.setEstado(estado);
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

	@Transactional
	public LineaTiempo actualizar(Integer id, LineaTiempo request) {

		LineaTiempo linea = lineaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Linea de tiempo no encontrada con ID: " + id));

		Proceso proceso = procesoRepository.findById(request.getProceso().getId()).orElseThrow(
				() -> new RuntimeException("Proceso no encontrado con ID: " + request.getProceso().getId()));

		Estado estado = estadoRepository.findById(request.getEstado().getId())
				.orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + request.getEstado().getId()));

		// Actualizar el estado en proceso
		proceso.setEstado(estado);
		proceso.setFechaactualizacion(LocalDateTime.now());
		procesoRepository.save(proceso);

		// Guardar linea de tiempo
		linea.setProceso(proceso);
		linea.setEstado(estado);
		linea.setUbicacion(request.getUbicacion());
		linea.setDescripcion(request.getDescripcion());
		linea.setFechaactualizacion(LocalDateTime.now());

		return lineaRepository.save(linea);
	}

	public boolean eliminar(Integer id) {
		if (lineaRepository.existsById(id)) {
			lineaRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
