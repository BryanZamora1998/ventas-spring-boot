package com.sistema.ventas.bo;

import java.util.List;
import java.util.Map;

import com.sistema.ventas.dto.ConsultarRolesDTO;
import com.sistema.ventas.dto.ConsultarRolesRutaUsuarioDTO;
import com.sistema.ventas.dto.CrearRolDTO;
import com.sistema.ventas.dto.RolesDTO;
import com.sistema.ventas.exceptions.BOException;

public interface IRolesBO {

	/**
	 * Consulta los roles que tiene el usuario que inicia la sesion
	 * 
	 * @author Bryan Zamora
	 * @param  username
	 * @return
	 * @throws BOException
	 */
	List<ConsultarRolesDTO> consultarRolesUsuarioSesion(String username)throws BOException;
	
	/**
	 * Consulta los roles que tiene las rutas 
	 * 
	 * @author Bryan Zamora
	 * @param  username
	 * @return
	 * @throws BOException
	 */
	List<ConsultarRolesDTO> consultarRolesRuta(String strRuta)throws BOException;

	/**
	 * Consulta el Arbol url del rol que tiene el usuario.
	 * 
	 * @author Bryan Zamora
	 * @param  username
	 * @param intSecuenciaRol
	 * @return
	 * @throws BOException
	 */
	List<ConsultarRolesRutaUsuarioDTO> consultarRolesRutaUsuario(String username, Integer intSecuenciaRol)throws BOException;

	/**
	 * Consulta todos los roles
	 * 
	 * @author Bryan Zamora
	 * @param intPage 
	 * @param intPerPage 
	 * @param  username
	 * @return
	 * @throws BOException
	 */
	Map<String, Object> consultarRoles(Integer intPerPage, Integer intPage)throws BOException;

	/**
	 * Guarda la url asignado al rol
	 * 
	 * @author Bryan Zamora
	 * @param  lsSecuenciaRutas
	 * @param intSecuenciaRol
	 * @param username
	 * @return
	 * @throws BOException
	 */
	void guardaRolesPorUrl(List<Integer> lsSecuenciaRutas, Integer intSecuenciaRol, String username) throws BOException;

	/**
	 * Crea el rol segun el modulo
	 * 
	 * @author Bryan Zamora
	 * @param  objCrearRol
	 * @param username
	 * @return
	 * @throws BOException
	 */
	void crearRol(CrearRolDTO objCrearRol, String username)throws BOException;

	/**
	 * Guarda los roles que se va a asignar al usuario
	 * 
	 * @author Bryan Zamora
	 * @param  username
	 * @param objRoles
	 * @param intSecuenciaUsuario
	 * @return
	 * @throws BOException
	 */
	void guardaRolesUsuario(String username, List<Integer> objRoles, Integer intSecuenciaUsuario)throws BOException;

	/**
	 * Consulta los roles que tiene asiganado el usuario
	 * 
	 * @author Bryan Zamora
	 * @param  intSecuenciaUsuario
	 * @return
	 * @throws BOException
	 */
	List<ConsultarRolesDTO> consultarRolesXUsuario(Integer intSecuenciaUsuario)throws BOException;


	/**
	 * Elimina el rol
	 * 
	 * @author Bryan Zamora
	 * @param  intSecuenciaUsuario
	 * @return
	 * @throws BOException
	 */
	void eliminarRol(Integer intSecuenciaRol) throws BOException;

	/**
	 * Consulta rol por id
	 * 
	 * @author Bryan Zamora
	 * @param  intSecuenciaRol
	 * @return
	 * @throws BOException
	 */
	RolesDTO consultarRolId(Integer intSecuenciaRol) throws BOException;

	/**
	 * Actualiza el rol
	 * 
	 * @author Bryan Zamora
	 * @param  objRolesDTO
	 * @param  strUsuario
	 * @return
	 * @throws BOException
	 */
	void actualizarRol(RolesDTO objRolesDTO, String strUsuario) throws BOException;

}
