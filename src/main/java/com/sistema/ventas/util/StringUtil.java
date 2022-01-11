package com.sistema.ventas.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper de validaciones de cadenas.
 * 
 * @author Brian Torres
 *
 */
public class StringUtil {
	
	/**
	 * Codifica un string a Base64 seguro de usar en URL.
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static String base64Encode(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}
	
	/**
	 * Decodifica un string que se encuentra codificado en Base64Url. 
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static String base64Decode(String input) {
		return new String(Base64.getDecoder().decode(input));
	}
	
	/**
	 * Concatena apellidos y nombres.
	 * 
	 * @author Brian Torres
	 * @param primerApellido
	 * @param segundoApellido
	 * @param primerNombre
	 * @param segundoNombre
	 * @return
	 */
	public static String concatenarApellidosNombres(String primerApellido, String segundoApellido, String primerNombre,
			String segundoNombre) {
		String strNombreCompleto = "";
		strNombreCompleto = primerApellido != null && !primerApellido.trim().equals("") ? primerApellido : "";
		strNombreCompleto = strNombreCompleto + (segundoApellido != null && !segundoApellido.trim().equals("") ? " " + segundoApellido : "");
		strNombreCompleto = strNombreCompleto + (primerNombre != null && !primerNombre.trim().equals("") ? " " + primerNombre : "");
		strNombreCompleto = strNombreCompleto + (segundoNombre != null && !segundoNombre.trim().equals("") ? " " + segundoNombre : "");
		return strNombreCompleto.trim().toUpperCase();
	}
	
	
	/**
	 * Valida cantidad de digitos de una cadeana.
	 * 
	 * @author Brian Torres
	 * @param cadena
	 * @param cantidadDigitos
	 * @return
	 */
	public static boolean cantidadDigitosValida(String cadena, int cantidadDigitos) {
		if (cadena != null && !cadena.trim().equals("")) {
			final String VALID = ("^\\d{"+cantidadDigitos+"}");
			return cadena.matches(VALID);
		}
		return false;
	}

	/**
	 * Valida si solamente existen letras en una cadena.
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static boolean soloLetras(String input) {
		String regx = "^[\\p{L}]+$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	/**
	 * Valida si solamente existen letras y espacios en blanco en una cadena.
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static boolean soloLetrasYEspacio(String input) {
		String regx = "^[\\p{L} ]+$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	/**
	 * Valida si solamente existen numeros en una cadena.
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static boolean soloNumeros(String input) {
		String regx = "\\d+";
		return input.matches(regx);
	}
	
	/**
	 * Valida si solamente existen letras y numeros en una cadena.
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static boolean soloLetrasYNumeros(String input) {
		String regx = "^[a-zA-Z0-9]+$";
		return input.matches(regx);
	}
	
	/**
	 * Elimina acentos de una cadena.
	 * 
	 * @author Brian Torres
	 * @param input
	 * @return
	 */
	public static String eliminarAcentos(String input) {

		final String ORIGINAL = "ÁáÉéÍíÓóÚúÜü";
		final String REEMPLAZO = "AaEeIiOoUuUu";

		if (input == null) {
			return null;
		}
		char[] array = input.toCharArray();
		for (int indice = 0; indice < array.length; indice++) {
			int pos = ORIGINAL.indexOf(array[indice]);
			if (pos > -1) {
				array[indice] = REEMPLAZO.charAt(pos);
			}
		}
		return new String(array);
	}
	
	
    /**
     * Este método devuelve una cadena aleatoria generada con SecureRandom
     * @author Bryan
     * @param length
     * @return 
     */
    public static String generateRandomString(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();
    
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            // 0-62 (exclusivo), retorno aleatorio 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}
