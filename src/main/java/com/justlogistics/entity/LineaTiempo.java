package com.justlogistics.entity;


import java.time.LocalDate;
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
@Table(name="lineatiempo")
@Data
public class LineaTiempo {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El proceso es obligatorio")
    @ManyToOne
    @JoinColumn(name = "proceso", referencedColumnName = "id")
    private Proceso proceso;

	@NotNull(message = "El estado es obligatorio")
    @ManyToOne
    @JoinColumn(name = "estado", referencedColumnName = "id")
    private Estado estado;

	@NotBlank(message = "La descripcion es obligatoria")
	@Size(max = 300, min = 2, message = "La descripcion  debe tener entre 2 y 100 caractéres")
	private String descripcion;
	
	@NotBlank(message = "La ubicacion es obligatoria")
	@Size(max = 100, min = 2, message = "La ubicacion  debe tener entre 2 y 100 caractéres")
	private String ubicacion;
	
	@NotNull(message = "La fecha evento es obligatoria")
	@Column(name = "fechaevento")
	private LocalDate fechaevento;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechacreacion")
	private LocalDateTime fechacreacion;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechaactualizacion")
	private LocalDateTime fechaactualizacion;

	
}

