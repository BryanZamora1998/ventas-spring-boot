package com.sistema.ventas.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import com.sistema.ventas.enums.FormatoEdad;
import com.sistema.ventas.enums.FormatoFecha;

/**
 * Helper de calculo y manejo de edades.
 * 
 * @author Brian Torres
 *
 */
public class EdadUtil {

	/**
	 * Calcula la edad a partir de una fecha de nacimiento.
	 * 
	 * @author Brian Torres
	 * @param fechaNacimiento Fecha de nacimiento.
	 * @param formatoEdad     Formato de edad: SHORT: Edad expresada máximo en una
	 *                        medida: [Anios, Meses, Dias]. MEDIUM: Edad expresada
	 *                        hasta máximo dos unidades: [Anios, Meses, Dias,
	 *                        Anios/Meses, Meses/Dias]. LONG: Edad expresada hasta
	 *                        máximo tres unidades: [Anios, Meses, Dias,
	 *                        Anios/Meses, Meses/Dias, Anios/Meses/Días].
	 * @return Retorna null si no es posible calcular la edad.
	 */
	public static String convertirFechaNacimientoAEdad(Date fechaNacimiento, FormatoEdad formatoEdad) {
		return calculaEdad(fechaNacimiento, formatoEdad);
	}

	/**
	 * Calcula la edad a partir de una fecha de nacimiento.
	 * 
	 * @author Brian Torres
	 * @param fechaNacimiento Fecha de nacimiento String en formato dd/MM/yyyy
	 * @param formatoEdad     Formato de edad: SHORT: Edad expresada máximo en una
	 *                        medida: [Anios, Meses, Dias]. MEDIUM: Edad expresada
	 *                        hasta máximo dos unidades: [Anios, Meses, Dias,
	 *                        Anios/Meses, Meses/Dias]. LONG: Edad expresada hasta
	 *                        máximo tres unidades: [Anios, Meses, Dias,
	 *                        Anios/Meses, Meses/Dias, Anios/Meses/Días].
	 * @return Retorna null si no es posible calcular la edad.
	 */
	public static String convertirFechaNacimientoAEdad(String fechaNacimiento, FormatoEdad formatoEdad) {
		if (!FechasUtil.formatoFechaValido(fechaNacimiento, FormatoFecha.DD_MM_YYYY)) {
			throw new RuntimeException("El formato de fecha no es valido.");
		}
		Date datFechaNacimiento = FechasUtil.stringToDate(fechaNacimiento, FormatoFecha.DD_MM_YYYY);
		return calculaEdad(datFechaNacimiento, formatoEdad);
	}

	/**
	 * Calcula la edad a partir de una fecha de nacimiento.
	 * 
	 * @param fechaNacimiento
	 * @param formatoEdad
	 * @return
	 */
	private static String calculaEdad(Date fechaNacimiento, FormatoEdad formatoEdad) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaNacimiento);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		LocalDate l1 = LocalDate.of(year, month, date);
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		String strEdad = null;

		String strAnios = getAniosDelPeriodo(diff1);
		String strMeses = getMesesDelPeriodo(diff1);
		String strDias = getDiasDelPeriodo(diff1);

		if (FormatoEdad.SHORT.equals(formatoEdad)) {
			if (strAnios != null)
				return strAnios;
			if (strMeses != null)
				return strMeses;
			if (strDias != null)
				return strDias;
			return strEdad;
		} else if (FormatoEdad.MEDIUM.equals(formatoEdad)) {
			int i = 0;
			if (strAnios != null) {
				strEdad = strAnios;
				i++;
			}
			if (strMeses != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strMeses;
				if (++i >= 2)
					return strEdad;
			}
			if (strDias != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strDias;
				if (++i >= 2)
					return strEdad;
			}
			return strEdad;
		} else if (FormatoEdad.LONG.equals(formatoEdad)) {
			if (strAnios != null) {
				strEdad = strAnios;
			}
			if (strMeses != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strMeses;
			}
			if (strDias != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strDias;
			}
			return strEdad;
		} else {
			throw new RuntimeException("El formato de edad no es valido.");
		}
	}

	/**
	 * Obtiene anios de diferencia en un periodo.
	 * 
	 * @author Brian Torres
	 * @param diff1
	 * @return
	 */
	private static String getAniosDelPeriodo(Period diff1) {
		if (diff1.getYears() > 0 && diff1.getYears() < 2) {
			return diff1.getYears() + " AÑO";
		} else if (diff1.getYears() > 1) {
			return diff1.getYears() + " AÑOS";
		}
		return null;
	}

	/**
	 * Obtiene meses de diferencia en un periodo.
	 * 
	 * @author Brian Torres
	 * @param diff1
	 * @return
	 */
	private static String getMesesDelPeriodo(Period diff1) {
		if (diff1.getMonths() > 0 && diff1.getMonths() < 2) {
			return diff1.getMonths() + " MES";
		} else if (diff1.getMonths() > 1) {
			return diff1.getMonths() + " MESES";
		}
		return null;
	}

	/**
	 * Obtiene dias de diferencia en un periodo.
	 * 
	 * @author Brian Torres
	 * @param diff1
	 * @return
	 */
	private static String getDiasDelPeriodo(Period diff1) {
		if (diff1.getDays() > 0 && diff1.getDays() < 2) {
			return diff1.getDays() + " DÍA";
		} else if (diff1.getDays() > 1) {
			return diff1.getDays() + " DÍAS";
		}
		return null;
	}

	/**
	 * Evalua si la edad en base a una fecha de nacimiento, se encuentra dentro de
	 * un rango de anios indicado.
	 * 
	 * @author Brian Torres
	 * @param fechaNacimiento Fecha de nacimiento en String.
	 * @param formatoFecha Formato de la fecha.
	 * @param edadMinima Edad minima.
	 * @param edadMaxima Edad maxima.
	 * @return
	 */
	public static boolean rangoEdadValidoFechaNacimiento(String fechaNacimiento, FormatoFecha formatoFecha,
			int edadMinima, int edadMaxima) {
		Date datFechaNacimiento = FechasUtil.stringToDate(fechaNacimiento, formatoFecha);
		Calendar c = Calendar.getInstance();
		c.setTime(datFechaNacimiento);
		int year = c.get(Calendar.YEAR);
		LocalDate l1 = LocalDate.of(year, c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		if (diff1.getYears() < edadMinima || diff1.getYears() > edadMaxima) {
			return false;
		}
		return true;
	}
	
	/**
	 * Evalua si la edad en base a una fecha de nacimiento, se encuentra dentro de
	 * un rango de anios indicado.
	 * 
	 * @author Brian Torres
	 * @param fechaNacimiento Fecha de nacimiento en Date
	 * @param edadMinima Edad minima.
	 * @param edadMaxima Edad maxima.
	 * @return
	 */
	public static boolean rangoEdadValidoFechaNacimiento(Date fechaNacimiento, int edadMinima, int edadMaxima) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaNacimiento);
		int year = c.get(Calendar.YEAR);
		LocalDate l1 = LocalDate.of(year, c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		if (diff1.getYears() < edadMinima || diff1.getYears() > edadMaxima) {
			return false;
		}
		return true;
	}
	
	
	
	/**********************************************************************************************/
	/**
	 * Calcula la edad a partir de una fecha de nacimiento.
	 * 
	 * @author Brian Torres
	 * @author Bryan Zamora : Ajuste del metodo a Lenguaje
	 * @param fechaNacimiento Fecha de nacimiento String en formato dd/MM/yyyy
	 * @param formatoEdad     Formato de edad: SHORT: Edad expresada máximo en una
	 *                        medida: [Anios, Meses, Dias]. MEDIUM: Edad expresada
	 *                        hasta máximo dos unidades: [Anios, Meses, Dias,
	 *                        Anios/Meses, Meses/Dias]. LONG: Edad expresada hasta
	 *                        máximo tres unidades: [Anios, Meses, Dias,
	 *                        Anios/Meses, Meses/Dias, Anios/Meses/Días].
	 * @param strLanguage
	 * @return Retorna null si no es posible calcular la edad.
	 */
	public static String convertirFechaNacimientoAEdad(String fechaNacimiento, FormatoEdad formatoEdad,String strLanguage) {
		if (!FechasUtil.formatoFechaValido(fechaNacimiento, FormatoFecha.YYYY_MM_DD_GUION)) {
			throw new RuntimeException("El formato de fecha no es valido.");
		}
		Date datFechaNacimiento = FechasUtil.stringToDate(fechaNacimiento, FormatoFecha.YYYY_MM_DD_GUION);
		return calculaEdad(datFechaNacimiento, formatoEdad,strLanguage);
	}

	/**
	 * Calcula la edad a partir de una fecha de nacimiento.
	 * @author Bryan Zamora : Ajuste del metodo a Lenguaje
	 * @param fechaNacimiento
	 * @param formatoEdad
	 * @param strLanguage
	 * @return
	 */
	private static String calculaEdad(Date fechaNacimiento, FormatoEdad formatoEdad, String strLanguage) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaNacimiento);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		LocalDate l1 = LocalDate.of(year, month, date);
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		String strEdad = null;

		String strAnios = getAniosDelPeriodo(diff1,strLanguage);
		String strMeses = getMesesDelPeriodo(diff1,strLanguage);
		String strDias = getDiasDelPeriodo(diff1,strLanguage);

		if (FormatoEdad.SHORT.equals(formatoEdad)) {
			if (strAnios != null)
				return strAnios;
			if (strMeses != null)
				return strMeses;
			if (strDias != null)
				return strDias;
			return strEdad;
		} else if (FormatoEdad.MEDIUM.equals(formatoEdad)) {
			int i = 0;
			if (strAnios != null) {
				strEdad = strAnios;
				i++;
			}
			if (strMeses != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strMeses;
				if (++i >= 2)
					return strEdad;
			}
			if (strDias != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strDias;
				if (++i >= 2)
					return strEdad;
			}
			return strEdad;
		} else if (FormatoEdad.LONG.equals(formatoEdad)) {
			if (strAnios != null) {
				strEdad = strAnios;
			}
			if (strMeses != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strMeses;
			}
			if (strDias != null) {
				strEdad = (strEdad != null ? strEdad + " " : "") + strDias;
			}
			return strEdad;
		} else {
			throw new RuntimeException("El formato de edad no es valido.");
		}
	}

	/**
	 * Obtiene anios de diferencia en un periodo.
	 * 
	 * @author Brian Torres
	 * @author Bryan Zamora : Ajuste del metodo a Lenguaje
	 * @param diff1
	 * @param strLanguage
	 * @return
	 */
	public static String getAniosDelPeriodo(Period diff1,String strLanguage) {
		if (diff1.getYears() > 0 && diff1.getYears() < 2) {
			return diff1.getYears()+" "+MensajesUtil.getMensaje("ven.etiquetas.anio", 
					MensajesUtil.validateSupportedLocale(strLanguage));
		} else if (diff1.getYears() > 1) {
			return diff1.getYears()+" "+MensajesUtil.getMensaje("ven.etiquetas.anios", 
					MensajesUtil.validateSupportedLocale(strLanguage));
		}
		return null;
	}

	/**
	 * Obtiene meses de diferencia en un periodo.
	 * 
	 * @author Brian Torres
	 * @author Bryan Zamora : Ajuste del metodo a Lenguaje
	 * @param diff1
	 * @param strLanguage
	 * @return
	 */
	public static String getMesesDelPeriodo(Period diff1,String strLanguage) {
		if (diff1.getMonths() > 0 && diff1.getMonths() < 2) {
			return diff1.getMonths()+" "+MensajesUtil.getMensaje("ven.etiquetas.mes", 
					MensajesUtil.validateSupportedLocale(strLanguage));
		} else if (diff1.getMonths() > 1) {
			return diff1.getMonths()+" "+MensajesUtil.getMensaje("ven.etiquetas.meses",
					MensajesUtil.validateSupportedLocale(strLanguage));
		}
		return null;
	}

	/**
	 * Obtiene dias de diferencia en un periodo.
	 * 
	 * @author Brian Torres
	 * @author Bryan Zamora : Ajuste del metodo a Lenguaje
	 * @param diff1
	 * @param strLanguage
	 * @return
	 */
	public static String getDiasDelPeriodo(Period diff1,String strLanguage) {
		if (diff1.getDays() > 0 && diff1.getDays() < 2) {
			return diff1.getDays() +" "+MensajesUtil.getMensaje("ven.etiquetas.dia",
					MensajesUtil.validateSupportedLocale(strLanguage));
		} else if (diff1.getDays() > 1) {
			return diff1.getDays()+" "+MensajesUtil.getMensaje("ven.etiquetas.dias",
					MensajesUtil.validateSupportedLocale(strLanguage));
		}
		return null;
	}


}
