package com.sistema.ventas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import com.sistema.ventas.enums.FormatoFecha;

public class FechasUtil {

	/**
	 * Valida si el formato de una fecha en String es correcto.
	 * 
	 * @author Brian Torres
	 * @param strDate      Fecha en String
	 * @param formatoFecha Tipo de formato de fecha a evaluar.
	 * @return
	 */
	public static boolean formatoFechaValido(String strDate, FormatoFecha formatoFecha) {
		if (strDate.trim().equals("")) {
			return false;
		}
		SimpleDateFormat sdfrmt = new SimpleDateFormat(formatoFecha.getName());
		sdfrmt.setLenient(false);
		try {
			sdfrmt.parse(strDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Convierte una fecha string a tipo de dato java.util.Date
	 * 
	 * @author Ivan Marriott
	 * @param strFecha
	 * @return
	 */
	public static Date stringToDate(String strFecha, FormatoFecha formatoFecha) {
		if (!formatoFechaValido(strFecha, formatoFecha)) {
			throw new RuntimeException("La fecha no cumple con el formato indicado.");
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFecha.getName());
			return formatter.parse(strFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convierte una fecha Date a String
	 * 
	 * @author Brian Torres
	 * @param datFecha
	 * @param formatoFecha
	 * @return
	 */
	public static String dateToString(Date datFecha, FormatoFecha formatoFecha) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatoFecha.getName());
		return formatter.format(datFecha);
	}

	/**
	 * Recibe y valida un String en un formato de fecha especifico y retorna un
	 * nuevo String con un nuevo formato.
	 * 
	 * @author Ivan Marriott
	 * @param strFecha
	 * @param formatoFechaEntrada
	 * @param formatoFechaSalida
	 * @return
	 */
	public static String stringToDateToString(String strFecha, FormatoFecha formatoFechaEntrada, FormatoFecha formatoFechaSalida) {
		if (Objects.nonNull(strFecha) && Objects.nonNull(formatoFechaEntrada) && Objects.nonNull(formatoFechaSalida)) {
			if (!formatoFechaValido(strFecha, formatoFechaEntrada)) {
				throw new RuntimeException("La fecha no cumple con el formato indicado.");
			}
			try {
				SimpleDateFormat formatterEntrada = new SimpleDateFormat(formatoFechaEntrada.getName());
				SimpleDateFormat formatterSalida = new SimpleDateFormat(formatoFechaSalida.getName());
				return formatterSalida.format(formatterEntrada.parse(strFecha));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Valida si la fecha indicada es futura con respecto al momento actual.
	 * 
	 * @author Brian Torres
	 * @param fecha        Fecha en String
	 * @param formatoFecha Formato de fecha.
	 * @return
	 */
	public static boolean esFechaFutura(String fecha, FormatoFecha formatoFecha) {
		Date datFecha = stringToDate(fecha, formatoFecha);
		return new Date().before(datFecha);
	}

	/**
	 * Valida si la fecha indicada es futura con respecto al momento actual.
	 * 
	 * @author Brian Torres
	 * @param fecha fecha en Date
	 * @return
	 */
	public static boolean esFechaFutura(Date fecha) {
		return new Date().before(fecha);
	}

	/**
	 * Convierte un objeto de tipo java.time.LocalDateTime a java.util.Date
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		if (localDateTime != null) {
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		} else {
			return null;
		}
	}

	/**
	 * Convierte de Date a LocalDate
	 * 
	 * @author Bryan Zamora
	 * @param date
	 */
	public static LocalDate convertToLocalDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day= c.get(Calendar.DATE);
		return LocalDate.of(year, month, day);
	}
	
	/**
	 * Eliminar horas a la fecha
	 * 
	 * @author Raúl Yugcha
	 * @param date
	 */
	
	public static Date limpiarHoraAFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
    }
	
	/**
	 *  Calcular el transcurso de dias entre dos fechas ejemplo: 
	 * 	1 año 2 meses 15 días,
	 *	2 meses 15 días,
	 *	15 días
	 * 
	 * @author Bryan Zamora
	 * @param datFechaInicio
	 * @param datFechaFin
	 * @param strLanguage     
	 */
	public static String calcularTranscursoDeDiasEntreFechas(Date datFechaInicio,Date datFechaFin, String strLanguage) {
		
		if(datFechaInicio==null || datFechaFin==null) 
			return null;
		
		LocalDate l1 = convertToLocalDate(limpiarHoraAFecha(datFechaInicio));
		LocalDate l2 = convertToLocalDate(limpiarHoraAFecha(datFechaFin));
		
		Period diff1 = Period.between(l1,l2);

		String strAnios = EdadUtil.getAniosDelPeriodo(diff1,strLanguage);
		String strMeses =EdadUtil.getMesesDelPeriodo(diff1,strLanguage);
		String strDias = EdadUtil.getDiasDelPeriodo(diff1,strLanguage);

		if(strAnios!=null && strMeses!=null && strDias!=null) 
			return strAnios+" "+strMeses+" "+strDias;
		else if(strMeses!=null && strDias!=null) 
			return strMeses+" "+strDias;
		else if(strDias!=null) 
			return strDias;
		else 
			return "0 "+MensajesUtil.getMensaje("ven.etiquetas.dia",
					MensajesUtil.validateSupportedLocale(strLanguage));
	}
	
	/**
	 * Convierte un objeto de tipo java.util.Date a LocalDateTime
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		if (date != null) {
			return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		} else {
			return null;
		}
	}
	
	public static Date fechaActual(String timezone) {
		LocalDateTime ldtFechaActual = LocalDateTime.now(ZoneId.of(timezone));
		return Date.from(ldtFechaActual.atZone(ZoneId.systemDefault()).toInstant());
	}
	
}
