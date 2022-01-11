package com.sistema.ventas.api;

import java.util.List;

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

import com.sistema.ventas.bo.IRolesBO;
import com.sistema.ventas.dto.CrearRolDTO;
import com.sistema.ventas.dto.ResponseOk;
import com.sistema.ventas.dto.RolesDTO;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.exceptions.CustomExceptionHandler;
import com.sistema.ventas.util.MensajesUtil;

@RestController
@RequestMapping("/roles")
public class RolesApi {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(ModulosApi.class.getName());
	
		@Autowired
		private IRolesBO objIRolesBO;
		
		@RequestMapping(value="/usuario",method = RequestMethod.GET)
		public ResponseEntity<?> consultarRolesUsuario(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage
				) throws BOException {
			
			try {
				
				UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						objIRolesBO.consultarRolesUsuarioSesion(objUserDetails.getUsername())), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
		@RequestMapping(value="/usuario/{secuenciaUsuario}",method = RequestMethod.PUT)
		public ResponseEntity<?> guardaRolesUsuario(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
				@PathVariable(value="secuenciaUsuario", required = false)  Integer  intSecuenciaUsuario,
				@RequestBody List<Integer> objRoles
				) throws BOException {
			
			try {
				
				UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				objIRolesBO.guardaRolesUsuario(objUserDetails.getUsername(),objRoles,intSecuenciaUsuario);
				
				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						null), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
		
		@RequestMapping(value="/ruta", method = RequestMethod.GET)
		public ResponseEntity<?> consultarRolesRuta(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
				@RequestParam(	value = "ruta", 	required = false) String strRuta
				) throws BOException {
			
			try {
				
				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						objIRolesBO.consultarRolesRuta(strRuta)), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
		@RequestMapping(value="/ruta/usuario/{secuenciaRol}", method = RequestMethod.GET)
		public ResponseEntity<?> consultarRolesRutaUsuario(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
				@PathVariable(value="secuenciaRol", required = false)  Integer  intSecuenciaRol
				) throws BOException {
			
			try {
				
				UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						objIRolesBO.consultarRolesRutaUsuario(objUserDetails.getUsername(),intSecuenciaRol)), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
		@RequestMapping(value="/usuario/{secuenciaUsuario}", method = RequestMethod.GET)
		public ResponseEntity<?> consultarRolesXUsuario(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
				@PathVariable(value="secuenciaUsuario", required = false)  Integer  intSecuenciaUsuario
				) throws BOException {
			
			try {
				
				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						objIRolesBO.consultarRolesXUsuario(intSecuenciaUsuario)), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<?> consultarRoles(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
				@RequestParam(	value = "page", 	required = false) Integer intPage,
				@RequestParam(	value = "perPage", 	required = false) Integer intPerPage
				) throws BOException {
			
			try {
				
				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						objIRolesBO.consultarRoles(intPerPage,intPage)), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
		@RequestMapping(value="/ruta/usuario/{secuenciaRol}",method = RequestMethod.PUT)
		public ResponseEntity<?> guardaRolesPorUrl(
				@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
				@PathVariable(value="secuenciaRol", required = false)  Integer  intSecuenciaRol,
				@RequestBody List<Integer> lsSecuenciaRutas
				) throws BOException {
			
			try {
				
				UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				objIRolesBO.guardaRolesPorUrl(lsSecuenciaRutas,intSecuenciaRol,objUserDetails.getUsername());
				
				return new ResponseEntity<>(new ResponseOk(
						MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
						null), HttpStatus.OK);
			} catch (BOException be) {
				logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
				throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
			}
		}
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> crearRol(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestBody CrearRolDTO objCrearRol
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			objIRolesBO.crearRol(objCrearRol,objUserDetails.getUsername());
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.rolCreado", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/{secuenciaRol}",method = RequestMethod.DELETE)
	public ResponseEntity<?> eleminarRol(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(value="secuenciaRol", required = false)  Integer  intSecuenciaRol
			) throws BOException {
		
		try {
			
			objIRolesBO.eliminarRol(intSecuenciaRol);
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/{secuenciaRol}",method = RequestMethod.GET)
	public ResponseEntity<?> consultarRolId(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(value="secuenciaRol", required = false)  Integer  intSecuenciaRol
			) throws BOException {
		
		try {
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIRolesBO.consultarRolId(intSecuenciaRol)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> actualizaRol(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestBody RolesDTO objRolesDTO
			) throws BOException {
		
		try {
			
			UserDetails objUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			objIRolesBO.actualizarRol(objRolesDTO,objUserDetails.getUsername());
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.rolActualizado", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
