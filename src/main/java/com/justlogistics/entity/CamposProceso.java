package com.justlogistics.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="camposproceso")
@Data
public class CamposProceso {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El proceso es obligatorio")
    @ManyToOne
    @JoinColumn(name = "proceso", referencedColumnName = "id")
    private Proceso proceso;

	@NotBlank(message = "La clave es obligatorio")
	@Size(max = 20, min = 2, message = "La clave debe tener entre 2 y 20 caractéres")
	private String clave ;

	@NotBlank(message = "El valor es obligatoria")
	@Size(max = 25, min = 2, message = "El valor  debe tener entre 2 y 25 caractéres")
	private String valor;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechacreacion")
	private LocalDateTime fechacreacion;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechaactualizacion")
	private LocalDateTime fechaactualizacion;

	
}

