package com.justlogistics.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.CamposProceso;
import com.justlogistics.entity.Proceso;
import com.justlogistics.repository.CamposProcesoRepository;
import com.justlogistics.repository.ProcesoRepository;

@Service
public class CamposProcesoService {

	@Autowired
	private CamposProcesoRepository camposRepository;
	
	@Autowired
	private ProcesoRepository procesoRepository;

	public CamposProceso guardar(CamposProceso request) {
		
		Proceso proceso = procesoRepository.findById(request.getProceso().getId()).orElseThrow(
				() -> new RuntimeException("Proceso no encontradado con ID: " + request.getProceso().getId()));
		
		request.setProceso(proceso);
		request.setFechacreacion(LocalDateTime.now());
		request.setFechaactualizacion(LocalDateTime.now());
		return camposRepository.save(request);
	}

	public List<CamposProceso> listar() {
		return camposRepository.findAll();
	}

	public CamposProceso get(Integer id) {
		return camposRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Campo proceso no encontrado con ID: " + id));
	}

	public CamposProceso actualizar(Integer id, CamposProceso request) {
		
		
		CamposProceso camposPro = camposRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Campo Proceso no encontrado con ID: " + id));
		
		Proceso proceso = procesoRepository.findById(request.getProceso().getId()).orElseThrow(
				() -> new RuntimeException("Proceso no encontradao con ID: " + request.getProceso().getId()));
		
		
		camposPro.setProceso(proceso);
		camposPro.setClave(request.getClave());
		camposPro.setValor(request.getValor());
		camposPro.setFechaactualizacion(LocalDateTime.now());
		return camposRepository.save(camposPro);
	}

	public boolean eliminar(Integer id) {
		if (camposRepository.existsById(id)) {
			camposRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
