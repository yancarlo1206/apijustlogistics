package com.justlogistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.Usuario;
import com.justlogistics.entity.UsuarioTipo;
import com.justlogistics.repository.UsuarioRepository;
import com.justlogistics.repository.UsuarioTipoRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioTipoRepository UsuarioTipoRepository;

	public Usuario guardar(Usuario request) {

		UsuarioTipo usuarioTipo = UsuarioTipoRepository.findById(request.getUsuariotipo().getId())
				.orElseThrow(() -> new RuntimeException(
						"Tipo de usuario no encontrado con ID: " + request.getUsuariotipo().getId()));

		request.setUsuariotipo(usuarioTipo);

		return usuarioRepository.save(request);
	}

	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	public Usuario get(Integer id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
	}

	public Usuario actualizar(Integer id, Usuario request) {
		Usuario actual = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

		UsuarioTipo usuarioTipo = UsuarioTipoRepository.findById(request.getUsuariotipo().getId())
				.orElseThrow(() -> new RuntimeException(
						"Tipo de usuario no encontrado con ID: " + request.getUsuariotipo().getId()));

		actual.setUsuariotipo(usuarioTipo);
		actual.setCorreo(request.getCorreo());
		return usuarioRepository.save(actual);
	}

	public boolean eliminar(Integer id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
