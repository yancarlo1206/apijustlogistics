package com.justlogistics.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justlogistics.entity.Estado;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.EstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/estado")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@PostMapping
	public ResponseEntity<ApiResponseJust<?>> guardar(@Valid @RequestBody Estado request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			Estado creado = estadoService.guardar(request);
			return ResponseEntity
					.ok(new ApiResponseJust<>("Estado creado correctamente.", HttpStatus.OK.value(), creado));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponseJust<?>> listar() {
		try {
			List<Estado> Estados = estadoService.listar();
			return ResponseEntity
					.ok(new ApiResponseJust<>("Listado obtenido correctamente.", HttpStatus.OK.value(), Estados));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> get(@PathVariable Integer id) {
		try {
			Estado Estado = estadoService.get(id);
			return ResponseEntity.ok(new ApiResponseJust<>("Estado encontrado.", HttpStatus.OK.value(), Estado));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Estado request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			Estado actualizado = estadoService.actualizar(id, request);
			return ResponseEntity.ok(new ApiResponseJust<>("Estado actualizado.", HttpStatus.OK.value(), actualizado));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> eliminar(@PathVariable Integer id) {
		try {
			boolean eliminado = estadoService.eliminar(id);

			if (eliminado) {
				return ResponseEntity.ok(new ApiResponseJust<>("Estado eliminado.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseJust<>(
						"Estado no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

}
