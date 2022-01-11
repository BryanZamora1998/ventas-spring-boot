package com.sistema.ventas.enums;

public enum FormatoEdad {

	SHORT("SHORT"),//Edad expresada máximo en una medida: [Anios, Meses, Dias].
	MEDIUM("MEDIUM"),//Edad expresada hasta máximo dos unidades: [Anios, Meses, Dias, Anios/Meses, Meses/Dias].
	LONG("LONG")//Edad expresada hasta máximo tres unidades: [Anios, Meses, Dias, Anios/Meses, Meses/Dias, Anios/Meses/Días].
	;

	private String name;

	FormatoEdad(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
