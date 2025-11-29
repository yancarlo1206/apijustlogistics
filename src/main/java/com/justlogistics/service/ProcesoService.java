package com.justlogistics.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.Proceso;
import com.justlogistics.repository.ClienteRepository;
import com.justlogistics.repository.ProcesoRepository;
import com.justlogistics.repository.TipoTransporteRepository;
import com.justlogistics.entity.Cliente;
import com.justlogistics.entity.TipoTransporte;

@Service
public class ProcesoService {

	@Autowired
	private ProcesoRepository ProcesoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TipoTransporteRepository tipoRepository;

	public Proceso guardar(Proceso request) {
		
		Cliente cliente = clienteRepository.findById(request.getCliente().getId()).orElseThrow(
				() -> new RuntimeException("Cliente no encontradao con ID: " + request.getCliente().getId()));
		
		TipoTransporte tipo = tipoRepository.findById(request.getCliente().getId()).orElseThrow(
				() -> new RuntimeException("Tipo Transporte no encontradao con ID: " + request.getCliente().getId()));
		
		
		request.setCliente(cliente);
		request.setTipotransporte(tipo);
		request.setFechacreacion(LocalDateTime.now());
		request.setFechaactualizacion(LocalDateTime.now());
		return ProcesoRepository.save(request);
	}

	public List<Proceso> listar() {
		return ProcesoRepository.findAll();
	}

	public Proceso get(Integer id) {
		return ProcesoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Proceso no encontrado con ID: " + id));
	}

	public Proceso actualizar(Integer id, Proceso request) {
		Proceso proceso = ProcesoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Proceso no encontrado con ID: " + id));
		
		Cliente cliente = clienteRepository.findById(request.getCliente().getId()).orElseThrow(
				() -> new RuntimeException("Cliente no encontradao con ID: " + request.getCliente().getId()));
		
		TipoTransporte tipo = tipoRepository.findById(request.getCliente().getId()).orElseThrow(
				() -> new RuntimeException("Tipo Transporte no encontradao con ID: " + request.getCliente().getId()));
		
		proceso.setCliente(cliente);
		proceso.setNombrecontenedor(request.getNombrecontenedor());
		proceso.setTipotransporte(tipo);
		proceso.setEstado(request.getEstado());
		proceso.setFechainicio(request.getFechainicio());
		proceso.setFechafinalizacion(request.getFechafinalizacion());
		proceso.setFechaactualizacion(LocalDateTime.now());
		return ProcesoRepository.save(proceso);
	}

	public boolean eliminar(Integer id) {
		if (ProcesoRepository.existsById(id)) {
			ProcesoRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
