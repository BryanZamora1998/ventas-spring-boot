package com.sistema.ventas.model;

import java.io.Serializable;

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

/**
 * Entity implementation class for Entity: Empresas
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbl_empresas")
public class Empresas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "secuencia_empresa")
    private Integer secuenciaEmpresa;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "descripcion")
    private String descripcion;
	
	@Column(name = "es_activo")
    private String esActivo;
	
	@Column(name = "logo")
	private byte[] logo;
}
