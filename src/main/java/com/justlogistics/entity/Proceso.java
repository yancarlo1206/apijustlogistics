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
@Table(name="proceso")
@Data
public class Proceso {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El cliente es obligatorio")
    @ManyToOne
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    private Cliente cliente;

	@NotBlank(message = "El contenedor es obligatorio")
	@Size(max = 100, min = 2, message = "El contenedor debe tener entre 2 y 20 caractéres")
	private String nombrecontenedor ;

	@NotNull(message = "El Tipo de transporte es obligatorio")
    @ManyToOne
    @JoinColumn(name = "tipotransporte", referencedColumnName = "id")
    private TipoTransporte tipotransporte;

	@NotNull(message = "El estado es obligatorio")
    @ManyToOne
    @JoinColumn(name = "estado", referencedColumnName = "id")
    private Estado estado;
	
	
	@NotNull(message = "La fecha finalización es obligatoria")
	@Column(name = "fechafinalizacion")
	private LocalDate fechafinalizacion;
	
	@NotNull(message = "La fecha inicio es obligatoria")
	@Column(name = "fechainicio")
	private LocalDate fechainicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechacreacion")
	private LocalDateTime fechacreacion;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fechaactualizacion")
	private LocalDateTime fechaactualizacion;

	
}
