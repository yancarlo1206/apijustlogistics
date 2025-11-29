package com.justlogistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="estado")
@Data
public class Estado {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	
	@NotBlank(message = "El estado es obligatoria")
	@Size(max=25, min= 2, message = "El estado  debe tener entre 2 y 25 caractéres")
	private String descripcion;

}
