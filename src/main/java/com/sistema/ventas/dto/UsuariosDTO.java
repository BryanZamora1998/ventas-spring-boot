package com.sistema.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuariosDTO {

	private Integer secuenciaTipoIdentificacion;
	private String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fechaNacimiento;
    private Integer secuenciaGenero;
    private Integer secuenciaPais;
    private Integer secuenciaProvincia;
    private Integer secuenciaCiudad;
    private String usuario; 
    private String email;
	private Integer secuenciaUsuario;
	private String direccion;
	private String telefonoMovil;
	private String telefonoFijo;
}
