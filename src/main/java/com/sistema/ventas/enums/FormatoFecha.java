package com.sistema.ventas.enums;


public enum FormatoFecha {

	DD_MM_YYYY("dd-MM-yyyy"),
	DD_MM_YYYY_HH_MM("dd-MM-yyyy HH:mm"),
	DD_MM_YYYY_HH_MM_SS("dd/MM/yyyy HH:mm:ss"),
	YYYY_MM_DD("yyyy/MM/dd"),
	YYYY_MM_DD_HH_MM("yyyy/MM/dd HH:mm"),
	YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss"),
	MM_DD_YYYYS("MM/dd/yyyy"),
	MM_DD_YYYY_HH_MM("MM/dd/yyyy HH:mm"),
	MM_DD_YYYY_HH_MM_SS("MM/dd/yyyy HH:mm:ss"),
	HH_MM("HH:MM"),
	hh_mm_aa("hh:mm aa"),
	YYYY_MM_DD_GUION("yyyy-MM-dd");

	private String name;

	FormatoFecha(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
