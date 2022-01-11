package com.sistema.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RolesDTO {
	private Integer secuenciaRol;
	private String nombre;
	private Integer secuenciaModulo;
}
