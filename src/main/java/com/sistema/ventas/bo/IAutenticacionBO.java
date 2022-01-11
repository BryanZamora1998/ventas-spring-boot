package com.sistema.ventas.bo;

import com.sistema.ventas.dto.AutenticacionDTO;
import com.sistema.ventas.exceptions.BOException;


public interface IAutenticacionBO {

	/**
	 * Inicia session 
	 * 
	 * @author Bryan Zamora
	 * @param strAuth
	 * @return
	 * @throws BOException
	 */
	public AutenticacionDTO login(String strAuth) throws BOException;

}
