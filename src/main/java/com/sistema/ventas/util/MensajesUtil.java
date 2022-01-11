package com.sistema.ventas.util;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class MensajesUtil {

	private static ResourceBundle bundle;
	private static String ficheroMensajes = "mensajes_ventas";
	public static final Locale Locale = new Locale("es", "EC");
	private static final String LOCALE_ES="es-EC";
	private static final String LOCALE_ING="en-US";

	public static String getMensaje(String strKey, Locale locale) {
		bundle = ResourceBundle.getBundle(ficheroMensajes, locale);	
		return new String(bundle.getString(strKey).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}

	public static String getMensaje(String strKey, Object[] arrParametros, Locale locale) {
		bundle = ResourceBundle.getBundle(ficheroMensajes, locale);
		String strMensaje = new String(bundle.getString(strKey).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		if (arrParametros != null && arrParametros.length > 0) {
			for (int i = 0; i < arrParametros.length; i++) {
				strMensaje = strMensaje.replace("{" + i + "}",
						(isKey(arrParametros[i].toString())
								? new String(bundle.getString(arrParametros[i].toString())
										.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)
								: arrParametros[i].toString()));
			}
		}
		return strMensaje;
	}
	
	private static boolean isKey(String strKey) {
		if (strKey != null && (strKey.contains(".warn.") || strKey.contains(".error.") || strKey.contains(".info.")
				|| strKey.contains(".campos.")))
			return true;
		else
			return false;
	}
	
	public static String getMensajeCampoObligatorio(String strKeyCampo, Locale locale) {
		return MensajesUtil.getMensaje("ven.warn.campoObligatorio",
				new Object[] { MensajesUtil.getMensaje(strKeyCampo, locale) }, locale);
	}
	
	public static Locale validateSupportedLocale(String strLanguage) {
		if(strLanguage == null || (!LOCALE_ES.equals(strLanguage) && !LOCALE_ING.equals(strLanguage)))
			strLanguage=LOCALE_ES;
		String[] arrLang = strLanguage.split("-");
    	return new Locale(arrLang[0], arrLang[1]);
	}

}
