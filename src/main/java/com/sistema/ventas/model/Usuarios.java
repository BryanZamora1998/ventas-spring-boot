package com.sistema.ventas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tbl_usuarios")
public class Usuarios  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "SECUENCIA_USUARIO")
    private Integer secuenciaUsuario;
	
	@Size(max=50)
	@Column(name = "usuario")
	private String usuario;
	
	@Size(max=50)
	@Column(name = "contrasenia")
	private String contrasenia;
	
	@Size(max=1)
	@Column(name = "es_activo")
	private String esActivo;
	
	@Size(max=1)
	@Column(name = "es_primera_vez")
	private String esPrimeraVez;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	
	@Size(max=50)
	@Column(name = "usuario_ingreso")
	private String usuarioIngreso;
	 
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;
	
	@Size(max=50)
	@Column(name = "usuario_actualizacion")
	private String usuarioActualizacion;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "secuencia_persona", referencedColumnName = "secuencia_persona")
	private Personas personas;
}
