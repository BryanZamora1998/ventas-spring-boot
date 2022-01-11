package com.sistema.ventas.bo;

import java.util.Map;

import com.sistema.ventas.dto.EnviarEmailDTO;
import com.sistema.ventas.exceptions.BOException;

public interface IGeneralBO {

	/**
	 * Consulta edad
	 * 
	 * @author Bryan Zamora
	 * @param strLanguage
	 * @param strFechaNacimiento
	 * @return
	 * @throws BOException
	 */
	public Map<String,Object> consultarFechaNacimiento(String strLanguage, String strFechaNacimiento)throws BOException;

	/**
	 * Enviar email
	 * 
	 * @author Bryan Zamora
	 * @param objEnviarEmail
	 * @return
	 * @throws BOException
	 */
	public void enviarEmailDTO(EnviarEmailDTO objEnviarEmail)throws BOException;

}
