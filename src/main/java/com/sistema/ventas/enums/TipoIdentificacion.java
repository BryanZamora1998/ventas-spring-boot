package com.sistema.ventas.enums;

public enum TipoIdentificacion {

	RUC(new Integer("2")),
	CEDULA(new Integer("1"));

	private Integer valor;

	TipoIdentificacion(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}
