package com.sistema.ventas.bo.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.ventas.bo.IGeneralBO;
import com.sistema.ventas.dto.EnviarEmailDTO;
import com.sistema.ventas.email.SendEmail;
import com.sistema.ventas.enums.FormatoEdad;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.util.EdadUtil;

@Service
public class GeneralBOImpl implements IGeneralBO{

	@Autowired
	private SendEmail objSendEmail;
	
	@Override
	public Map<String,Object> consultarFechaNacimiento(String strLanguage, String strFechaNacimiento) throws BOException {
		
		if (ObjectUtils.isEmpty(strFechaNacimiento)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.fechaNacimiento"});
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("edad", EdadUtil.convertirFechaNacimientoAEdad(strFechaNacimiento, FormatoEdad.SHORT, strLanguage));
		
		return map;
	}

	@Override
	public void enviarEmailDTO(EnviarEmailDTO objEnviarEmail) throws BOException {
		if (ObjectUtils.isEmpty(objEnviarEmail.getEmail())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.email"});
		
		if (ObjectUtils.isEmpty(objEnviarEmail.getAsunto())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.asunto"});
		
		if (ObjectUtils.isEmpty(objEnviarEmail.getContenido())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.contenido"});
		
		objSendEmail.envioEmail(objEnviarEmail.getEmail(), objEnviarEmail.getAsunto(), objEnviarEmail.getContenido());
	}

}
