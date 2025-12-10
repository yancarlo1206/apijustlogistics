package com.justlogistics.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="cliente")
@Data
public class Cliente {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@NotBlank(message = "El NIT es obligatorio")
	@Size(max=20, min= 2, message = "El NIT debe tener entre 2 y 20 caractéres")
	private String nit;
	
	@NotBlank(message = "La razon Social es obligatoria")
	@Size(max=100, min= 2, message = "La razon Social  debe tener entre 2 y 25 caractéres")
	private String razonsocial;
	
	@NotBlank(message = "El nombre de contacto es obligatorio")
	@Size(max=25, min= 2, message = "El nombre de contacto debe tener entre 2 y 25 caractéres")
	private String nombrecontacto;
	
	@NotBlank(message = "El apellido es obligatorio")
	@Size(max=25, min= 2, message = "El apellido debe tener entre 2 y 25 caractéres")
	private String apellidocontacto;
	
	@Email(message = "El formato de correo no es correcto")
	@NotBlank(message = "El correo es obligatorio")
	private String correo;
	
	@NotBlank(message = "El teléfono es obligatorio.")
	@Pattern(regexp = "\\d+", message = "El teléfono debe ser numérico.")
	@Column(length = 20)
	private String telefono;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechacreacion")
	private LocalDateTime fechacreacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechaactualizacion")
	private LocalDateTime fechaactualizacion;
	
	private String username;
	

}
	
