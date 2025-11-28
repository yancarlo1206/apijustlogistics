package com.justlogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justlogistics.dto.AuthRequest;
import com.justlogistics.dto.AuthResponse;
import com.justlogistics.dto.RegisterRequest;
import com.justlogistics.service.AuthService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			String token = authService.login(request.getUsername(), request.getPassword());
			return ResponseEntity.ok(new AuthResponse(token));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		try {
			authService.register(request);
			return ResponseEntity.ok("Usuario registrado correctamente");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
