package com.sistema.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AutenticacionDTO {
	private Integer secuenciaSistemaUsuario;
	private String primerNombre;
	private String primerApellido;
	private String usuario;
	private String token;
	private String rolSistema;
	private byte[] photo;
	public Boolean esContrasenaPrimeraVez;
}
