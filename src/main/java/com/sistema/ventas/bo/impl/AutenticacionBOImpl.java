package com.sistema.ventas.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.sistema.ventas.bo.IAutenticacionBO;
import com.sistema.ventas.dao.UsuariosDAO;
import com.sistema.ventas.dto.AutenticacionDTO;
import com.sistema.ventas.enums.AuthenticationScheme;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.model.Usuarios;
import com.sistema.ventas.segurity.JwtUtil;
import com.sistema.ventas.util.SeguridadUtil;

@Service
public class AutenticacionBOImpl implements IAutenticacionBO{

	@Autowired
	private UsuariosDAO objUsuarioSistemaDAO;
	@Autowired
	private JwtUtil jwUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	@Transactional
	public AutenticacionDTO login(String strBasic) throws AuthenticationException, BOException {
				
		String[] strAuth=SeguridadUtil.obtenerBasicAuth(strBasic,AuthenticationScheme.BASIC.name());
		AutenticacionDTO objAut=null; 
		
		try {
			strAuth[0]=strAuth[0].toUpperCase();
			
			Usuarios objUsuario=objUsuarioSistemaDAO.consultarUsuarioSistema(strAuth[0]);
			
			if(objUsuario==null) {
				throw new BOException("ven.warn.usuarioNoExiste", new Object[] {strAuth[0]});
			}else {
				
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(strAuth[0],strAuth[1]));
				objAut=new AutenticacionDTO();
				objAut.setSecuenciaSistemaUsuario(objUsuario.getSecuenciaUsuario());
				objAut.setPrimerApellido(objUsuario.getPersonas().getPrimerApellido());
				objAut.setPrimerNombre(objUsuario.getPersonas().getPrimerNombre());
				objAut.setUsuario(objUsuario.getUsuario());
				objAut.setToken(jwUtil.generateToken(strAuth[0]));
				objAut.setPhoto(objUsuario.getPersonas().getFoto());
				objAut.setEsContrasenaPrimeraVez(objUsuario.getEsPrimeraVez()!=null && "S".equalsIgnoreCase(objUsuario.getEsPrimeraVez())?true:false);
			}
		
		} catch (BadCredentialsException e) {
			throw new BOException("ven.warn.credencialesInvalidas");
		} 
		
		return objAut;
	}

}
