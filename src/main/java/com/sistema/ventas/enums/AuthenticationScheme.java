package com.sistema.ventas.enums;


public enum AuthenticationScheme {
	
	BEARER {
		@Override
		public String toString() {
			return "Bearer";
		}
	}, 
	BASIC {
		@Override
		public String toString() {
			return "Basic";
		}
	};
	
}