package com.sistema.ventas.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConsultarUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer secuenciaUsuario;
	private String numeroIdentificacion;
	private String nombresCompletos;
    private String usuario;
    private String email;
    private boolean estado;
    private List<ConsultarRolesDTO> roles;
}
