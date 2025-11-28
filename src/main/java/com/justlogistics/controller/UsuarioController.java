package com.justlogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justlogistics.entity.Usuario;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.UsuarioService;
import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponseJust<?>> guardar(@Valid @RequestBody Usuario request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Usuario creado = usuarioService.guardar(request);
			return ResponseEntity.ok(new ApiResponseJust<>("Usuario creado correctamente.", HttpStatus.OK.value(), creado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				    .body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}
	
	//Listar

	@GetMapping
	public ResponseEntity<ApiResponseJust<?>> listar() {
		List<Usuario> usuarios = usuarioService.listar();
		return ResponseEntity.ok(new ApiResponseJust<>("Listado obtenido correctamente.", HttpStatus.OK.value(), usuarios));
	}
	
	// Obtener ID
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> get(@PathVariable Integer id) {
		Usuario usuario = usuarioService.get(id);
		if (usuario != null) {
			return ResponseEntity.ok(new ApiResponseJust<>("Usuario encontrado.", HttpStatus.OK.value(), usuario));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>("Usuario no encontrado.", HttpStatus.NOT_FOUND.value(), null));
		}
	}
	
	// Actualizar 
		@PutMapping("/{id}")
		public ResponseEntity<ApiResponseJust<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Usuario request,
				BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}
			
			Usuario actualizado = usuarioService.actualizar(id,request);
			if (actualizado != null) {
				return ResponseEntity.ok(new ApiResponseJust<>("Usuario actualizado.", HttpStatus.OK.value(), actualizado));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new ApiResponseJust<>("Usuario no encontrado para actualizar.", HttpStatus.NOT_FOUND.value(), null));
			}
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<ApiResponseJust<?>> eliminar(@PathVariable Integer id) {

			boolean eliminado = usuarioService.eliminar(id);
			if (eliminado) {
				return ResponseEntity.ok(new ApiResponseJust<>("Articulo eliminado.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new ApiResponseJust<>("Usuario no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		}
		
}

