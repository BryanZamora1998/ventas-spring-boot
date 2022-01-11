package com.sistema.ventas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tbl_rutas_x_roles")
public class RutasXRoles implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@EqualsAndHashCode.Include
    private RutasXRolesCPK rutasXRolesCPK;
	
	@Size(max=1)
	@Column(name = "es_activo")
	private String esActivo;
	
	@Size(max=1)
	@Column(name = "es_select")
	private String esSelect;
	
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
	@JoinColumn(name = "secuencia_rol", referencedColumnName = "secuencia_rol",insertable = false, updatable = false)
	private Roles roles;

}
