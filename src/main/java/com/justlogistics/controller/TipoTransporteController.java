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

import com.justlogistics.entity.TipoTransporte;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.TipoTransporteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/tipoTransporte")
public class TipoTransporteController {

	@Autowired
	private TipoTransporteService tipoService;

	@PostMapping
	public ResponseEntity<ApiResponseJust<?>> guardar(@Valid @RequestBody TipoTransporte request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			TipoTransporte creado = tipoService.guardar(request);
			return ResponseEntity
					.ok(new ApiResponseJust<>("TipoTransporte creado correctamente.", HttpStatus.OK.value(), creado));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponseJust<?>> listar() {
		try {
			List<TipoTransporte> TipoTransportes = tipoService.listar();
			return ResponseEntity
					.ok(new ApiResponseJust<>("Listado obtenido correctamente.", HttpStatus.OK.value(), TipoTransportes));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> get(@PathVariable Integer id) {
		try {
			TipoTransporte TipoTransporte = tipoService.get(id);
			return ResponseEntity.ok(new ApiResponseJust<>("TipoTransporte encontrado.", HttpStatus.OK.value(), TipoTransporte));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody TipoTransporte request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			TipoTransporte actualizado = tipoService.actualizar(id, request);
			return ResponseEntity.ok(new ApiResponseJust<>("TipoTransporte actualizado.", HttpStatus.OK.value(), actualizado));

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
			boolean eliminado = tipoService.eliminar(id);

			if (eliminado) {
				return ResponseEntity.ok(new ApiResponseJust<>("TipoTransporte eliminado.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseJust<>(
						"TipoTransporte no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

}
