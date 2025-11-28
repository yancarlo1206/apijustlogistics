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

import com.justlogistics.entity.LineaTiempo;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.LineaTiempoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/lineaTiempo")
public class LineaTiempoController {

	@Autowired
	private LineaTiempoService lineaService;

	@PostMapping
	public ResponseEntity<ApiResponseJust<?>> guardar(@Valid @RequestBody LineaTiempo request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			LineaTiempo creado = lineaService.guardar(request);
			return ResponseEntity
					.ok(new ApiResponseJust<>("Linea de tiempo creadoa correctamente.", HttpStatus.OK.value(), creado));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponseJust<?>> listar() {
		try {
			List<LineaTiempo> lineaTiempo = lineaService.listar();
			return ResponseEntity
					.ok(new ApiResponseJust<>("Linea de tiempo obtenida correctamente.", HttpStatus.OK.value(), lineaTiempo));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> get(@PathVariable Integer id) {
		try {
			LineaTiempo lineaTiempo = lineaService.get(id);
			return ResponseEntity.ok(new ApiResponseJust<>("Linea de tiempo encontrada.", HttpStatus.OK.value(), lineaTiempo));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody LineaTiempo request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			LineaTiempo actualizado = lineaService.actualizar(id, request);
			return ResponseEntity.ok(new ApiResponseJust<>("Linea de tiempo actualizada.", HttpStatus.OK.value(), actualizado));

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
			boolean eliminado = lineaService.eliminar(id);

			if (eliminado) {
				return ResponseEntity.ok(new ApiResponseJust<>("Linea de tiempo eliminada.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseJust<>(
						"Linea de tiempo no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

}
