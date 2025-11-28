package com.justlogistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name="usuario ")
@Data
public class Usuario {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@NotBlank(message = "El username es obligatorio")
	private String username;
	
	@Email(message = "El formato de email no es correcto")
	@NotBlank(message = "El correo es obligatorio")
	private String correo;
	
	@NotBlank(message = "El password es obligatorio")
	private String password;
	
    @ManyToOne
    @JoinColumn(name = "usuariotipo", referencedColumnName = "id")
    private UsuarioTipo usuariotipo;
	
	
}
