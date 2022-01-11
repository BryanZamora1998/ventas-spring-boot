package com.sistema.ventas.bo;

import java.util.List;

import com.sistema.ventas.dto.ConsultarModulosDTO;
import com.sistema.ventas.exceptions.BOException;

public interface IModulosBO {
	/**
	 * Consulta modulos
	 * 
	 * @author Bryan Zamora
	 * @param username
	 * @return
	 * @throws BOException
	 */
	public List<ConsultarModulosDTO> consultarModulos(String strUsername)throws BOException;
}
