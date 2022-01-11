package com.sistema.ventas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "tbl_usuario_x_roles")
public class UsuarioXRoles implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@EqualsAndHashCode.Include
    private UsuarioXRolesCPK usuariosXRolesCPK;
	
	@Size(max=1)
	@Column(name = "es_activo")
	private String esActivo;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	
	@Size(max=50)
	@Column(name = "usuario_ingreso")
	private String usuarioIngreso;
	 
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@Size(max=50)
	@Column(name = "usuario_actualizacion")
	private String usuarioActualizacion;

}
