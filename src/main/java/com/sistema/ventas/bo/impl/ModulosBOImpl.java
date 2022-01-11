package com.sistema.ventas.bo.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.ventas.bo.IModulosBO;
import com.sistema.ventas.dao.ModulosDAO;
import com.sistema.ventas.dao.UsuariosDAO;
import com.sistema.ventas.dto.ConsultarModulosDTO;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.model.Usuarios;

@Service
public class ModulosBOImpl implements IModulosBO{

	@Autowired
	private ModulosDAO objModulosDAO;
	@Autowired
	private UsuariosDAO objUsuariosDAO;
	
	@Override
	public List<ConsultarModulosDTO> consultarModulos(String strUsername) throws BOException {
		
		Usuarios objUsuario=objUsuariosDAO.consultarUsuarioSistemaPorCorreo(strUsername);
		
		List<ConsultarModulosDTO>ls=objModulosDAO.consultarModulos(objUsuario.getSecuenciaUsuario());
		
		if (ObjectUtils.isEmpty(ls))
			throw new BOException("ven.warn.usuarioSinModulos", new Object[] {strUsername});
		
		return ls;
	}

}
