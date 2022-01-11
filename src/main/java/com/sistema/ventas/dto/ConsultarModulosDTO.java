package com.sistema.ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConsultarModulosDTO {
	private Integer secuenciaModulo;
	private String nombre;
	private String url;
	private String img;
	private Boolean esSelect;
	private String mnemonico;
}
