package com.sistema.ventas.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.ventas.bo.IUsuariosBO;
import com.sistema.ventas.dto.ConsultarModulosDTO;
import com.sistema.ventas.dto.ContrasenaDTO;
import com.sistema.ventas.dto.ResponseOk;
import com.sistema.ventas.dto.UsuariosDTO;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.exceptions.CustomExceptionHandler;
import com.sistema.ventas.util.MensajesUtil;

@RestController
@RequestMapping("/usuarios")
public class UsuariosApi {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(UsuariosApi.class.getName());
	
	@Autowired
	private IUsuariosBO objIUsuariosBO;
	
	@RequestMapping(value="/crearUsuario",method = RequestMethod.POST)
	public ResponseEntity<?> crearUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody UsuariosDTO objUsuariosDTO
			) throws BOException {
		
		try {

			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.usuarioCreado", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIUsuariosBO.crearUsuario(objUsuariosDTO,objUserDetails.getUsername())), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/actualizarUsuario",method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario,
			@RequestBody UsuariosDTO objUsuariosDTO
			) throws BOException {
		
		try {

			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			objIUsuariosBO.actualizarUsuario(objUsuariosDTO,objUserDetails.getUsername());

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.usuarioActualizado", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> consultarUsuarios(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam(	value = "page", 	required = false) Integer intPage,
			@RequestParam(	value = "perPage", 	required = false) Integer intPerPage,
			@RequestParam(	value = "cedulaCodigoUsuario", 	required = false) String strCedulaCodigoUsuario,
			@RequestParam(	value = "estado", 	required = false) String strEstado
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Map<String,Object> mapConsultarUsuarioDTO=objIUsuariosBO.consultarUsuarios(intPage,intPerPage,strCedulaCodigoUsuario,strEstado,objUserDetails.getUsername());
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					mapConsultarUsuarioDTO), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/activarOInactivarUsuario/{idUsuario}",method = RequestMethod.PUT)
	@Transactional 
	public ResponseEntity<?> activarOInactivarUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario
			) throws BOException {
		
		try {
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIUsuariosBO.activarOInactivarUsuario(intIdUsuario,objUserDetails.getUsername())), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/{idUsuario}/basica",method = RequestMethod.GET)
	public ResponseEntity<?> consultarUsuarioXId(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario
			) throws BOException {
		
		try {
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIUsuariosBO.consultarUsuarioXId(intIdUsuario)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/photo",method = RequestMethod.POST)
	public ResponseEntity<?> guardarPhoto(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam("photo") MultipartFile photo,
			@RequestParam(value="idPersona", required = false)  Integer  intIdPersona
			) throws BOException, IOException {
		
		try {
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			objIUsuariosBO.guardarPhoto(photo,intIdPersona,objUserDetails.getUsername());

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/usuarioDisponible",method = RequestMethod.GET)
	public ResponseEntity<?> consultarUsuarioDisponible(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam(	value = "primerNombre", 	required = false) String strPrimerNombre,
			@RequestParam(	value = "segundoNombre", 	required = false) String strSegundoNombre,
			@RequestParam(	value = "primerApellido", 	required = false) String stPrimerApellido,
			@RequestParam(	value = "segundoApellido", 	required = false) String strSegundoApellido
			) throws BOException {
		
		try {
			
			Map<String,Object> mapConsultarUsuarioDTO=objIUsuariosBO.consultarUsuarioDisponible(strPrimerNombre,strSegundoNombre,stPrimerApellido,strSegundoApellido);
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					mapConsultarUsuarioDTO), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	
	@RequestMapping(value="/recuperarContrasena",method = RequestMethod.POST)
	public ResponseEntity<?> recuperarContrasena(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody ContrasenaDTO objCorreo
			) throws BOException {
		
		try {
			
			objIUsuariosBO.recuperarContrasena(objCorreo.getCorreo());
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.recuperarContrasena", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
		
	}
	
	@RequestMapping(value="/cambioContrasena",method = RequestMethod.POST)
	public ResponseEntity<?> cambioContrasena(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody ContrasenaDTO objContrasena
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			objIUsuariosBO.cambioContrasena(objContrasena.getContrasenia(),objUserDetails.getUsername());
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
		
	}
	
	@RequestMapping(value="/{idUsuario}/eliminarUsuario",method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminarUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			objIUsuariosBO.eliminarUsuario(intIdUsuario,objUserDetails.getUsername());
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
		
	}
	
	@RequestMapping(value="/modulos",method = RequestMethod.GET)
	public ResponseEntity<?> modulosUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam("incluirModulosNoParametrizados") Boolean incluirModulosNoParametrizados,
			@RequestParam("secuenciaUsuario") Integer intSecuenciaUsuario
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIUsuariosBO.modulosUsuario(objUserDetails.getUsername(),incluirModulosNoParametrizados,intSecuenciaUsuario,strLanguage)), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/modulos",method = RequestMethod.PUT)
	public ResponseEntity<?> modulosUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam("secuenciaUsuario") Integer intSecuenciaUsuario,
			@RequestBody List<ConsultarModulosDTO> objModulosDTO
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			objIUsuariosBO.modulosUsuarioActualizar(objUserDetails.getUsername(),intSecuenciaUsuario,objModulosDTO);
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
		
	}
	
	@RequestMapping(value="/modulos/permiso",method = RequestMethod.GET)
	public ResponseEntity<?> consultaPermisoModulo(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam("mnemonico") String strMnemonico
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIUsuariosBO.consultaPermisoModulo(objUserDetails.getUsername(),strMnemonico)), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
		
	}
}
