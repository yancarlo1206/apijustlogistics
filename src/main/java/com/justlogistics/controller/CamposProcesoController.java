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

import com.justlogistics.entity.CamposProceso;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.CamposProcesoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/camposProceso")
public class CamposProcesoController {

	@Autowired
	private CamposProcesoService camposProcesoService;

	@PostMapping
	public ResponseEntity<ApiResponseJust<?>> guardar(@Valid @RequestBody CamposProceso request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			CamposProceso creado = camposProcesoService.guardar(request);
			return ResponseEntity
					.ok(new ApiResponseJust<>("CamposProceso creado correctamente.", HttpStatus.OK.value(), creado));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponseJust<?>> listar() {
		try {
			List<CamposProceso> CamposProcesos = camposProcesoService.listar();
			return ResponseEntity
					.ok(new ApiResponseJust<>("Listado obtenido correctamente.", HttpStatus.OK.value(), CamposProcesos));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> get(@PathVariable Integer id) {
		try {
			CamposProceso CamposProceso = camposProcesoService.get(id);
			return ResponseEntity.ok(new ApiResponseJust<>("CamposProceso encontrado.", HttpStatus.OK.value(), CamposProceso));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody CamposProceso request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			CamposProceso actualizado = camposProcesoService.actualizar(id, request);
			return ResponseEntity.ok(new ApiResponseJust<>("CamposProceso actualizado.", HttpStatus.OK.value(), actualizado));

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
			boolean eliminado = camposProcesoService.eliminar(id);

			if (eliminado) {
				return ResponseEntity.ok(new ApiResponseJust<>("CamposProceso eliminado.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseJust<>(
						"CamposProceso no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

}