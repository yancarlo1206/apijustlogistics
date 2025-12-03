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

import com.justlogistics.entity.Proceso;
import com.justlogistics.repository.ProcesoRepository;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.ClienteService;
import com.justlogistics.service.ProcesoService;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/proceso")
public class ProcesoController {

	@Autowired
	private ProcesoService ProcesoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProcesoRepository procesoRepository;

	@PostMapping
	public ResponseEntity<ApiResponseJust<?>> guardar(@Valid @RequestBody Proceso request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			Proceso creado = ProcesoService.guardar(request);
			return ResponseEntity
					.ok(new ApiResponseJust<>("Proceso creado correctamente.", HttpStatus.OK.value(), creado));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponseJust<?>> listar() {
		try {
			List<Proceso> Procesos = ProcesoService.listar();
			return ResponseEntity
					.ok(new ApiResponseJust<>("Listado obtenido correctamente.", HttpStatus.OK.value(), Procesos));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> get(@PathVariable Integer id) {
		try {
			Proceso Proceso = ProcesoService.get(id);
			return ResponseEntity.ok(new ApiResponseJust<>("Proceso encontrado.", HttpStatus.OK.value(), Proceso));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Proceso request,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			Proceso actualizado = ProcesoService.actualizar(id, request);
			return ResponseEntity.ok(new ApiResponseJust<>("Proceso actualizado.", HttpStatus.OK.value(), actualizado));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseJust<?>> eliminar(@PathVariable Integer id) {
		try {
			boolean eliminado = ProcesoService.eliminar(id);

			if (eliminado) {
				return ResponseEntity.ok(new ApiResponseJust<>("Proceso eliminado.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseJust<>(
						"Proceso no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/listPorCliente")
	public ResponseEntity<ApiResponseJust<?>> procesosrPorCliente(Authentication authentication) {

		try {

			String username = authentication.getName();
			Integer clienteId = clienteService.obtenerClienteIdPorUsername(username);

			String nombreCliente = clienteService.obtenerNombreCliente(clienteId);

			List<Proceso> procesos = procesoRepository.findByClienteId(clienteId);

			if (procesos.isEmpty()) {
				return ResponseEntity
						.ok(new ApiResponseJust<>("El cliente " + nombreCliente + " no tiene procesos registrados.",
								HttpStatus.OK.value(), procesos));
			}

			return ResponseEntity.ok(new ApiResponseJust<>("Lista de procesos del cliente: " + nombreCliente + ".",
					HttpStatus.OK.value(), procesos));

		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseJust<>(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

}
