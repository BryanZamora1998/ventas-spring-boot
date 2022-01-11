package com.sistema.ventas.enums;

public enum AlgoritmosIdentificacion {

	CEDULA_IDENTIDAD_EC("CEDULA_IDENTIDAD_EC"),
	REGISTRO_UNICO_CONTRIBUYENTE_EC("REGISTRO_UNICO_CONTRIBUYENTE_EC"),
	PASAPORTE("PASAPORTE")
	;

	private String name;

    AlgoritmosIdentificacion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
	
	
}
