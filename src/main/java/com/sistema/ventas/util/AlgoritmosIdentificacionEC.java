package com.sistema.ventas.util;

public class AlgoritmosIdentificacionEC {

	/**
     * Link de SRI y Registro Civil:
     * https://servicios.registrocivil.gob.ec/cdd/
     * http://www.sri.gob.ec/web/guest/RUC
     * https://declaraciones.sri.gob.ec/sri-en-linea/#/SriRucWeb/ConsultaRuc/Consultas/consultaRuc
     * https://medium.com/@bryansuarez/c%C3%B3mo-validar-c%C3%A9dula-y-ruc-en-ecuador-b62c5666186f
     * http://telesjimenez.blogspot.com/2011/05/algoritmo-de-verificacion-de-ruc_21.html
     */
	
    public boolean validadorTipoIdentificacion(String strNumeroIdentificacion) {
        return validaNumeroIdentificacion(strNumeroIdentificacion);
    }
    
    public static boolean validaNumeroIdentificacion(String numeroIdentificacion) {
        int numeroProvincias = 24;

        if (numeroIdentificacion != null && numeroIdentificacion.trim().length() >= 10) {
            numeroIdentificacion = numeroIdentificacion.trim();

            if (numeroIdentificacion.matches("[0-9]*")) {
                int provincia = Integer.parseInt(numeroIdentificacion.charAt(0) + "" + numeroIdentificacion.charAt(1));
                int digitoTres = Integer.parseInt(numeroIdentificacion.charAt(2) + "");

                if (numeroIdentificacion.length() == 10) {
                    if (((provincia > 0 && provincia <= numeroProvincias) || provincia == 30) && digitoTres <= 6) {
                        if (validaCedulaRucPersonasNaturales(numeroIdentificacion)) {
                            return true;
                        }
                    }
                } else if (numeroIdentificacion.length() == 13) {
                    if (numeroIdentificacion.substring(10).equals("001")) {
                        if ((provincia > 0 && provincia <= numeroProvincias) && digitoTres < 6) {
                            if (validaCedulaRucPersonasNaturales(numeroIdentificacion)) {
                                return true;
                            }
                        } else if ((provincia > 0 && provincia <= numeroProvincias) && digitoTres == 9) {
                            if (validaRucInstitucionesPrivadas(numeroIdentificacion)) {
                                return true;
                            }
                        } else if ((provincia > 0 && provincia <= numeroProvincias) && digitoTres == 6) {
                            if (validaRucInstitucionesPublicas(numeroIdentificacion)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean validaCedulaRucPersonasNaturales(String numeroIdentificacion) {
        int total = 0;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int numeroProvincias = 24;
        int tercerDigito = 6;

        if (numeroIdentificacion.matches("[0-9]*") & ((numeroIdentificacion.length() == 10) || (numeroIdentificacion.length() == 13))) {
            int provincia = Integer.parseInt(numeroIdentificacion.charAt(0) + "" + numeroIdentificacion.charAt(1));
            int digitoTres = Integer.parseInt(numeroIdentificacion.charAt(2) + "");

            // Valida cedula y ruc personas naturales
            if (((provincia > 0 && provincia <= numeroProvincias) || provincia == 30) && (digitoTres <= tercerDigito)) {
                int digitoVerificadorRecibido = Integer.parseInt(numeroIdentificacion.charAt(9) + "");

                for (int i = 0; i < coeficientes.length; i++) {
                    int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(numeroIdentificacion.charAt(i) + "");
                    total = valor >= 10 ? total + (valor - 9) : total + valor;
                }
                int digitoVerificadorObtenido = (total % 10) != 0 ? 10 - (total % 10) : (total % 10);

                if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean validaRucInstitucionesPrivadas(String ruc) {
        int total = 0;
        int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
        int numeroProvincias = 24;
        int tercerDigito = 9;

        if (ruc.matches("[0-9]*") && ruc.length() == 13) {
            int provincia = Integer.parseInt(ruc.charAt(0) + "" + ruc.charAt(1));
            int digitoTres = Integer.parseInt(ruc.charAt(2) + "");

            // Valida RUC instituciones privadas
            if ((provincia > 0 && provincia <= numeroProvincias) && (digitoTres == tercerDigito)) {
                int digitoVerificadorRecibido = Integer.parseInt(ruc.charAt(9) + "");

                for (int i = 0; i < coeficientes.length; i++) {
                    int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(ruc.charAt(i) + "");
                    total = total + valor;
                }
                int digitoVerificadorObtenido = (total % 11) != 0 ? 11 - (total % 11) : (total % 11);

                if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean validaRucInstitucionesPublicas(String ruc) {
        int total = 0;
        int[] coeficientes = {3, 2, 7, 6, 5, 4, 3, 2};
        int numeroProvincias = 24;
        int tercerDigito = 6;

        if (ruc.matches("[0-9]*") && ruc.length() == 13) {
            int provincia = Integer.parseInt(ruc.charAt(0) + "" + ruc.charAt(1));
            int digitoTres = Integer.parseInt(ruc.charAt(2) + "");

            // Valida RUC instituciones publicas
            if ((provincia > 0 && provincia <= numeroProvincias) && (digitoTres == tercerDigito)) {
                int digitoVerificadorRecibido = Integer.parseInt(ruc.charAt(8) + "");

                for (int i = 0; i < coeficientes.length; i++) {
                    int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(ruc.charAt(i) + "");
                    total = total + valor;
                }
                int digitoVerificadorObtenido = (total % 11) != 0 ? 11 - (total % 11) : (total % 11);

                if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
}