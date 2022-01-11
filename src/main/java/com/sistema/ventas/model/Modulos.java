package com.sistema.ventas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbl_modulos")
public class Modulos {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "secuencia_modulo")
    private Integer secuenciaModulo	;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "nombre_en")
    private String nombreEn;
	
	@Column(name = "es_activo")
    private String esActivo;
	
	@Column(name = "img")
    private String img;
}
