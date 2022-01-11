
package com.sistema.ventas.bo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sistema.ventas.dto.ConsultarModulosDTO;
import com.sistema.ventas.dto.UsuariosDTO;
import com.sistema.ventas.exceptions.BOException;

public interface IUsuariosBO {

	/**
	 * Crea un nuevo usuario
	 * 
	 * @author Bryan Zamora
	 * @param objUsuariosDTO
	 * @param  strUsuario
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> crearUsuario(UsuariosDTO objUsuariosDTO,String strUsuario) throws BOException;

	/**
	 * Elimina un usuario de manera logica
	 * 
	 * @author Bryan Zamora
	 * @param  intIdUsuario
	 * @param  strUsuario
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> activarOInactivarUsuario(Integer intIdUsuario, String strUsuario)  throws BOException;

	/**
	 * Consulta Usuario por id
	 * 
	 * @author Bryan Zamora
	 * @param  intIdUsuario
	 * @return
	 * @throws BOException
	 */
	public UsuariosDTO consultarUsuarioXId(Integer intIdUsuario)  throws BOException;

	/**
	 * Actualiza el usuario
	 * 
	 * @author Bryan Zamora
	 * @param  objUsuariosDTO
	 * @return
	 * @throws BOException
	 */
	public void actualizarUsuario(UsuariosDTO objUsuariosDTO, String strUsuario)throws BOException;

	/**
	 * Guarda la foto de una persona
	 * 
	 * @author Bryan Zamora
	 * @param  photo
	 * @param  intIdPersona
	 * @param  strUsuario
	 * @return
	 * @throws BOException
	 */
	public void guardarPhoto(MultipartFile photo, Integer intIdPersona, String strUsuario)throws BOException, IOException;

	
	/**
	 * Consulta los usuarios para pagineo
	 * 
	 * @author Bryan Zamora
	 * @param  intPage
	 * @param  intPerPage
	 * @param  strCedulaCodigoUsuario
	 * @param  strEstado
	 * @param strUser 
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> consultarUsuarios(Integer intPage, Integer intPerPage, String strCedulaCodigoUsuario, String strEstado, String strUser)throws BOException;

	/**
	 * Consulta los usuario disponible
	 * 
	 * @author Bryan Zamora
	 * @param  intPage
	 * @param  intPerPage
	 * @param  strCedulaCodigoUsuario
	 * @param  strEstado
	 * @param strUser 
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> consultarUsuarioDisponible(String strPrimerNombre, String strSegundoNombre,
			String stPrimerApellido, String strSegundoApellido)throws BOException;

	/**
	 * Recuperar la contrasena del usuario
	 * 
	 * @author Bryan Zamora
	 * @return
	 * @throws BOException
	 */
	public void recuperarContrasena(String strCorreo)throws BOException;

	/**
	 * Cambiar contrasena
	 * 
	 * @author Bryan Zamora
	 * @return
	 * @throws BOException
	 */
	public void cambioContrasena(String contrasena, String username)throws BOException;

	/**
	 * Elimina el usuario
	 * 
	 * @author Bryan Zamora
	 * @param intIdUsuario
	 * @param strUsuario
	 * @return
	 * @throws BOException
	 */
	public void eliminarUsuario(Integer intIdUsuario,String strUsuario)throws BOException;

	/**
	 * Consulta los modulos que tiene asignado el usuario
	 * 
	 * @author Bryan Zamora
	 * @param incluirModulosParametrizados 
	 * @param strLanguage 
	 * @param strUsuario
	 * @return
	 * @throws BOException
	 */
	public Object modulosUsuario(String username, Boolean incluirModulosParametrizados,Integer intSecuenciaUsuario, String strLanguage) throws BOException;

	/**
	 * Actualizar los modulos del usuario
	 * 
	 * @author Bryan Zamora
	 * @param intSecuenciaUsuario 
	 * @param strUsuario
	 * @return
	 * @throws BOException
	 */
	public void modulosUsuarioActualizar(String username, Integer intSecuenciaUsuario,List<ConsultarModulosDTO> objModulosDTO) throws BOException;

	/**
	 * Consulta los si tiene permiso con el modulo
	 * 
	 * @author Bryan Zamora
	 * @param intSecuenciaUsuario 
	 * @param strUsuario
	 * @return
	 * @throws BOException
	 */
	public Boolean consultaPermisoModulo(String username, String strMnemonico) throws BOException;

}
