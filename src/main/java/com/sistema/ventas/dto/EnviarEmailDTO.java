package com.sistema.ventas.dto;

import lombok.Data;

@Data
public class EnviarEmailDTO {
	private String email; 
	private String asunto; 
	private String contenido;
}
