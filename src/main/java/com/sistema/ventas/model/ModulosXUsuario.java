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
@Table(name = "tbl_modulos_x_usuario")
public class ModulosXUsuario implements Serializable {

		private static final long serialVersionUID = 1L;

		@EmbeddedId
		@EqualsAndHashCode.Include
	    private ModulosXUsuarioCPK modulosXUsuarioCPK;
		
		@Size(max=1)
		@Column(name = "es_activo")
		private String esActivo;
		
		@Size(max=1)
		@Column(name = "es_select")
		private String esSelect;
}
