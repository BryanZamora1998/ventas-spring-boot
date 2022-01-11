package com.sistema.ventas.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.ventas.bo.IGeneralBO;
import com.sistema.ventas.dto.EnviarEmailDTO;
import com.sistema.ventas.dto.ResponseOk;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.exceptions.CustomExceptionHandler;
import com.sistema.ventas.util.MensajesUtil;

@RestController
@RequestMapping("/general")
public class GeneralApi {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(GeneralApi.class.getName());
	
	@Autowired
	private IGeneralBO objIGeneralBO;
	
	@RequestMapping(value="/calcularEdad",method = RequestMethod.GET)
	public ResponseEntity<?> consultarEdad(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam(	value = "fechaNacimiento", 	required = false) String strFechaNacimiento
			) throws BOException {
		
		try {

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objIGeneralBO.consultarFechaNacimiento(strLanguage,strFechaNacimiento)), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	@RequestMapping(value="/enviarEmail",method = RequestMethod.PUT)
	public ResponseEntity<?> enviarEmail(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody EnviarEmailDTO objEnviarEmail
			) throws BOException {
		
		try {

			objIGeneralBO.enviarEmailDTO(objEnviarEmail);
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
			
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(strLanguage));
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
