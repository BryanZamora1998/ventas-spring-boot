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
import javax.persistence.JoinColumns;
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
@Table(name = "tbl_personas")
public class Personas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "secuencia_persona")
    private Integer secuenciaPersona;
	
	@Size(max=50)
	@Column(name = "numero_identificacion")
	private String numeroIdentificacion;
	
	@Size(max=50)
    @Column(name = "primer_nombre")
    private String primerNombre;
	
	@Size(max=50)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
	
	@Size(max=50)
    @Column(name = "primer_apellido")
    private String primerApellido;
	
	@Size(max=50)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
	
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    
	@Size(max=1)
	@Column(name = "es_activo")
	private String esActivo;
	
	@Column(name = "foto")
	private byte[] foto;
	
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
	
	@Size(max=50)
	@Column(name = "email")
	private String email;
	
    @JoinColumns({
        @JoinColumn(name = "secuencia_pais", referencedColumnName = "secuencia_pais"),
        @JoinColumn(name = "secuencia_provincia", referencedColumnName = "secuencia_provincia"),
        @JoinColumn(name = "secuencia_ciudad", referencedColumnName = "secuencia_ciudad")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ciudad ciudad;

    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "secuencia_tipo_identificacion", referencedColumnName = "secuencia_tipo_identificacion", insertable = true, updatable = true)
	private TiposIdentificacion tiposIdentificacion;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "secuencia_GENERO", referencedColumnName = "secuencia_GENERO", insertable = true, updatable = true)
	private Generos genero;
    
    @Size(max=500)
	@Column(name = "direccion")
	private String direccion;
    
    @Size(max=10)
	@Column(name = "telefono_movil")
	private String telefonoMovil;
    
    @Size(max=10)
	@Column(name = "telefono_fijo")
	private String telefonoFijo;
}

