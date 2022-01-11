package com.sistema.ventas.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.resteasy.util.Base64;

import com.sistema.ventas.enums.AuthenticationScheme;
import com.sistema.ventas.exceptions.BOException;

public class SeguridadUtil {
	
	// Loggers
	private static final Logger logger = Logger.getLogger(SeguridadUtil.class.getName());

	
	/**
	 * 
	 * @author Bryan Zamora
	 * @param strAuthorization
	 * @return
	 * @throws BOException
	 */
	public static String[] obtenerBasicAuth(String strAuthorization, String strTipoAuth) throws BOException {
		String[] values = null;
		// VALIDA TIPO DE AUTORIZACION = BASIC
		if (strTipoAuth.equalsIgnoreCase(AuthenticationScheme.BASIC.toString())) {
			try {
				// DECODIFICA EL STRING BASE64
				String base64Credentials = strAuthorization.substring(AuthenticationScheme.BASIC.toString().length()).trim();
				String credentials = new String(Base64.decode(base64Credentials.getBytes()), Charset.forName("UTF-8"));
				// SEPARA AL STRING EN : Y LO GUARDA EN UN STRING[]
				values = credentials.split(":", 2);
				// SI LA VARIABLE TIENE 2 OBJETOS(USER Y PASSWORD) LOS RETORNA
				if (values.length == 2) {
					return values;
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, null, e);
				// ERROR AL DECODIFICAR LAS CREDENCIALES
				throw new BOException("ven.error.errorDecodeAuth");
			}
		}
		// TIPO DE AUTORIZACION NO SOPORTADA
		throw new BOException("ven.warn.tipoAutorizacionNoSoportada");
	}
}
