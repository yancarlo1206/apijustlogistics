package com.justlogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justlogistics.dto.AuthRequest;
import com.justlogistics.dto.AuthResponse;
import com.justlogistics.dto.RegisterRequest;
import com.justlogistics.response.ApiResponseJust;
import com.justlogistics.service.AuthService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponseJust<?>> login(@Valid @RequestBody AuthRequest request,
			BindingResult bindingResult) {

		try {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest()
						.body(new ApiResponseJust<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			String token = authService.login(request.getUsername(), request.getPassword());

			return ResponseEntity.ok(
					new ApiResponseJust<>("Inicio de sesión exitoso.", HttpStatus.OK.value(), new AuthResponse(token)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponseJust<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED.value(), null));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponseJust<?>> register(@RequestBody RegisterRequest request) {
		try {

			authService.register(request);

			return ResponseEntity
					.ok(new ApiResponseJust<>("Usuario registrado correctamente.", HttpStatus.OK.value(), null));

		} catch (RuntimeException e) {

			return ResponseEntity.badRequest()
					.body(new ApiResponseJust<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
		}
	}

}
