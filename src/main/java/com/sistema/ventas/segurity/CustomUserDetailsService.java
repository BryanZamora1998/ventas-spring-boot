package com.sistema.ventas.segurity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sistema.ventas.dao.UsuariosDAO;
import com.sistema.ventas.model.Usuarios;
import com.sistema.ventas.util.StringUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuariosDAO objUsuariosDAO;
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public UserDetails loadUserByUsername(String username){
		// TODO Auto-generated method stub
		Usuarios objUsuario=objUsuariosDAO.consultarUsuarioSistema(username);
		String strPassword;

		strPassword = StringUtil.base64Decode(objUsuario.getContrasenia());
		
		return new User(objUsuario.getUsuario(),strPassword,new ArrayList());

	}
}
