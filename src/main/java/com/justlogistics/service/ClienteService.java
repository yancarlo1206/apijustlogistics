package com.justlogistics.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.Cliente;
import com.justlogistics.repository.ClienteRepository;


import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private AuthService authService;

	@Transactional
    public Cliente guardar(Cliente request) {

        String correo = request.getCorreo();
        String username = correo.split("@")[0];

        request.setFechacreacion(LocalDateTime.now());
        request.setFechaactualizacion(LocalDateTime.now());
        request.setUsername(username);

        Cliente clienteGuardado = clienteRepository.save(request);
        
        // Crear usuario
        authService.crearUserCliente(clienteGuardado);

        return clienteGuardado;
    }

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente get(Integer id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
	}

	public Cliente actualizar(Integer id, Cliente request) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

		cliente.setNit(request.getNit());
		cliente.setRazonsocial(request.getRazonsocial());
		cliente.setNombrecontacto(request.getNombrecontacto());
		cliente.setApellidocontacto(request.getApellidocontacto());
		cliente.setCorreo(request.getCorreo());
		cliente.setTelefono(request.getTelefono());
		cliente.setFechaactualizacion(LocalDateTime.now());
		return clienteRepository.save(cliente);
	}

	public boolean eliminar(Integer id) {
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public String obtenerNombreCliente(Integer clienteId) {

		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con usuario: " + clienteId));

		return cliente.getNombrecontacto() + " " + cliente.getApellidocontacto();
	}

	public Integer obtenerClienteIdPorUsername(String username) {

		Cliente cliente = clienteRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + username));

		return cliente.getId();
	}

}
