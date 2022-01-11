package com.sistema.ventas.bo.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.ventas.bo.IRolesBO;
import com.sistema.ventas.dao.ModuloXRolesDAO;
import com.sistema.ventas.dao.ModulosDAO;
import com.sistema.ventas.dao.RolesDAO;
import com.sistema.ventas.dao.RutasXRolesDAO;
import com.sistema.ventas.dao.UsuarioXRolesDAO;
import com.sistema.ventas.dao.UsuariosDAO;
import com.sistema.ventas.daoRepository.IModuloXRolesDAO;
import com.sistema.ventas.daoRepository.IRolesDAO;
import com.sistema.ventas.daoRepository.IRutasXRolesDAO;
import com.sistema.ventas.daoRepository.IUsuarioXRolesDAO;
import com.sistema.ventas.dto.ConsultarRolesDTO;
import com.sistema.ventas.dto.ConsultarRolesRutaUsuarioDTO;
import com.sistema.ventas.dto.CrearRolDTO;
import com.sistema.ventas.dto.RolesDTO;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.model.ModuloXRoles;
import com.sistema.ventas.model.ModuloXRolesCPK;
import com.sistema.ventas.model.Modulos;
import com.sistema.ventas.model.Roles;
import com.sistema.ventas.model.RutasXRoles;
import com.sistema.ventas.model.RutasXRolesCPK;
import com.sistema.ventas.model.UsuarioXRoles;
import com.sistema.ventas.model.UsuarioXRolesCPK;
import com.sistema.ventas.model.Usuarios;
import com.sistema.ventas.util.StringUtil;

@Service
public class RolesBOImpl implements IRolesBO{

	@Autowired
	private RolesDAO objRolesDAO;
	@Autowired
	private UsuariosDAO objUsuariosDAO;
	@Autowired
    private RutasXRolesDAO objRutasXRolesDAO;
	@Autowired
    private UsuarioXRolesDAO objUsuariosXRolesDAO;
	@Autowired
    private ModulosDAO objModulosDAO;
	@Autowired
    private ModuloXRolesDAO objModuloXRolesDAO;
	@Autowired
    private IRutasXRolesDAO objIRutasXRolesDAO;
	@Autowired
    private IUsuarioXRolesDAO objIUsuarioXRolesDAO;
	@Autowired
    private IRolesDAO objIRolesDAO;
	@Autowired
    private IModuloXRolesDAO objIModuloXRolesDAO;

	@Override
	public List<ConsultarRolesDTO> consultarRolesUsuarioSesion(String username) throws BOException {
		
		Usuarios objUsuario=objUsuariosDAO.consultarUsuarioSistemaPorCorreo(username);
		
		return objRolesDAO.consultarRolesUsuarioSesion(objUsuario.getSecuenciaUsuario());
	}
	

	@Override
	public List<ConsultarRolesDTO> consultarRolesRuta(String strRuta) throws BOException {
		
		if (ObjectUtils.isEmpty(strRuta)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.ruta"});
		
		return objRolesDAO.consultarRolesRuta(strRuta);
	}


	@Override
	public List<ConsultarRolesRutaUsuarioDTO> consultarRolesRutaUsuario(String username, Integer intSecuenciaRol)
			throws BOException {
		
		if (ObjectUtils.isEmpty(intSecuenciaRol)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaRol"});
		
		Usuarios objUsuario=objUsuariosDAO.consultarUsuarioSistemaPorCorreo(username);
		
		return objRolesDAO.consultarRolesRutaUsuario(intSecuenciaRol,objUsuario.getSecuenciaUsuario(),true,null);
	}


	@Override
	public Map<String,Object>  consultarRoles(Integer intPerPage, Integer intPage) throws BOException {
		
		if (ObjectUtils.isEmpty(intPerPage)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.perPage"});
		
		if (ObjectUtils.isEmpty(intPage)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.page"});
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rows",objRolesDAO.consultarRoles(intPerPage,intPage));
		map.put("totalRows",objRolesDAO.countConsultarRoles());
		
		return map;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void guardaRolesPorUrl(List<Integer> lsSecuenciaRutas, Integer intSecuenciaRol, String username) throws BOException {
		
		Date dateFechaActual=new Date();
		
		//Requiere la secuencia de rutas
		if (ObjectUtils.isEmpty(intSecuenciaRol)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaRol"});
		
		List<RutasXRoles> lsRutasXRoles =objRutasXRolesDAO.findAllPorRol(intSecuenciaRol);
		
		for(RutasXRoles objRol:lsRutasXRoles) {
			objRol.setEsActivo("N");
			objRol.setFechaActualizacion(dateFechaActual);
			objRol.setUsuarioIngreso(username);
			objRutasXRolesDAO.update(objRol);
		}
		
		//Requiere los roles
		if (!ObjectUtils.isEmpty(lsSecuenciaRutas)) {
			Optional<RutasXRoles> optRutasXRoles;
			RutasXRoles objRutasXRoles;
			for(Integer intRuta:lsSecuenciaRutas) {
				optRutasXRoles=objRutasXRolesDAO.find(new RutasXRolesCPK(intSecuenciaRol,intRuta));
				
				if(!optRutasXRoles.isPresent()) {
					
					objRutasXRoles=new RutasXRoles();
					objRutasXRoles.setRutasXRolesCPK(new RutasXRolesCPK(intSecuenciaRol,intRuta));
					objRutasXRoles.setEsActivo("S");
					objRutasXRoles.setEsSelect("S");
					objRutasXRoles.setFechaIngreso(dateFechaActual);
					objRutasXRoles.setUsuarioIngreso(username);
					objRutasXRolesDAO.persist(objRutasXRoles);
					
				}else {
					
					if("N".equalsIgnoreCase(optRutasXRoles.get().getEsActivo())) {
						optRutasXRoles.get().setEsActivo("S");
						optRutasXRoles.get().setEsSelect("S");
						optRutasXRoles.get().setFechaActualizacion(dateFechaActual);
						optRutasXRoles.get().setUsuarioIngreso(username);
						objRutasXRolesDAO.update(optRutasXRoles.get());
					}
					
				}
			}
			
		}

	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void crearRol(CrearRolDTO objCrearRol,String strUsername) throws BOException {
		
		if (ObjectUtils.isEmpty(objCrearRol.getNombre())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.nombre"});
		
		Boolean booExiste=objRolesDAO.consultarRolesPorNombre(objCrearRol.getNombre());
		
		if(booExiste)
			throw new BOException("ven.warn.nombreRolExiste");
		
		if (ObjectUtils.isEmpty(objCrearRol.getSecuenciaModulo())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaModulo"});
		
		Optional<Modulos> objModulos=objModulosDAO.find(objCrearRol.getSecuenciaModulo());
		
		if(!objModulos.isPresent())
			throw new BOException("ven.warn.campoNoExiste", new Object[] { "ven.campos.secuenciaModulo"});
		
		if("N".equalsIgnoreCase(objModulos.get().getEsActivo()))
			throw new BOException("ven.warn.campoInactivo", new Object[] { "ven.campos.secuenciaModulo"});

		Roles objRol=new Roles();
		objRol.setNombre(StringUtil.eliminarAcentos(objCrearRol.getNombre().trim()));
		objRol.setEsActivo("S");
		objRolesDAO.persist(objRol);
		
		ModuloXRoles objModuloXRol=new ModuloXRoles();
		objModuloXRol.setModuloXRolesCPK(new ModuloXRolesCPK(objRol.getSecuenciaRol(),objCrearRol.getSecuenciaModulo()));
		objModuloXRol.setEsActivo("S");
		objModuloXRol.setFechaIngreso(new Date());
		objModuloXRol.setUsuarioIngreso(strUsername);
		objModuloXRolesDAO.persist(objModuloXRol);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void guardaRolesUsuario(String username, List<Integer> lsRoles,Integer intSecuenciaUsuario) throws BOException {
		
		Date dateFechaActual=new Date();
		
		//Requiere la secuencia de rutas
		if (ObjectUtils.isEmpty(intSecuenciaUsuario)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaUsuario"});
		
		Optional<Usuarios> objUsuarios=objUsuariosDAO.find(intSecuenciaUsuario);
		
		if (!objUsuarios.isPresent()) 
			throw new BOException("ven.warn.campoNoExiste", new Object[] { "ven.campos.secuenciaUsuario"});
				
		if ("N".equalsIgnoreCase(objUsuarios.get().getEsActivo())) 
			throw new BOException("ven.warn.campoInactivo", new Object[] { "ven.campos.secuenciaUsuario"});
		
		
		
		List<UsuarioXRoles> lsUsuarioXRoles =objUsuariosXRolesDAO.findRolAllPorUsuario(intSecuenciaUsuario);
		
		for(UsuarioXRoles objRol:lsUsuarioXRoles) {
			objRol.setEsActivo("N");
			objRol.setFechaModificacion(dateFechaActual);
			objRol.setUsuarioIngreso(username);
			objUsuariosXRolesDAO.update(objRol);
		}
		
		//Requiere los roles
		if (!ObjectUtils.isEmpty(lsRoles)) {
			Optional<UsuarioXRoles> optUsuariosXRoles;
			UsuarioXRoles objUsuariosXRoles;
			for(Integer intRol:lsRoles) {
				optUsuariosXRoles=objUsuariosXRolesDAO.find(new UsuarioXRolesCPK(intSecuenciaUsuario,intRol));
				
				if(!optUsuariosXRoles.isPresent()) {
					
					objUsuariosXRoles=new UsuarioXRoles();
					objUsuariosXRoles.setUsuariosXRolesCPK(new UsuarioXRolesCPK(intSecuenciaUsuario,intRol));
					objUsuariosXRoles.setEsActivo("S");
					objUsuariosXRoles.setFechaIngreso(dateFechaActual);
					objUsuariosXRoles.setUsuarioIngreso(username);
					objUsuariosXRolesDAO.persist(objUsuariosXRoles);
					
				}else {
					
					if("N".equalsIgnoreCase(optUsuariosXRoles.get().getEsActivo())) {
						optUsuariosXRoles.get().setEsActivo("S");
						optUsuariosXRoles.get().setFechaModificacion(dateFechaActual);
						optUsuariosXRoles.get().setUsuarioIngreso(username);
						objUsuariosXRolesDAO.update(optUsuariosXRoles.get());
					}
					
				}
			}
			
		}
	}

	@Override
	public List<ConsultarRolesDTO> consultarRolesXUsuario(Integer intSecuenciaUsuario) throws BOException {
		
		//Valida que el campo usuario sea obligatorio
		if (ObjectUtils.isEmpty(intSecuenciaUsuario)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] {"ven.campos.idUsuario"});
		
		return objRolesDAO.consultarRolesUsuario(intSecuenciaUsuario);
	}


	@Override
	public void eliminarRol(Integer intSecuenciaRol) throws BOException {
		
		//Valida que el campo intSecuenciaRol sea requerida.
		if (ObjectUtils.isEmpty(intSecuenciaRol)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaRol"});
		
		Optional<Roles> objRoles=objIRolesDAO.findById(intSecuenciaRol);
		
		//Valida que exista el rol.
		if (!objRoles.isPresent()) 
			throw new BOException("ven.warn.campoNoExiste", new Object[] { "ven.campos.secuenciaRol"});
		
		List<UsuarioXRoles> lsUsuXRoles = objUsuariosXRolesDAO.findRolAllPorRol(intSecuenciaRol);
			
		//Elimina los roles en la entidad UsuarioXRoles
		if(!ObjectUtils.isEmpty(lsUsuXRoles)) {
			for(UsuarioXRoles objUsuXRoles:lsUsuXRoles) {
				
				if("S".equalsIgnoreCase(objUsuXRoles.getEsActivo()))
					throw new BOException("ven.warn.noEliminarRol");
				
				objIUsuarioXRolesDAO.delete(objUsuXRoles);
			}
		}
		
		List<RutasXRoles> lsRutasXRoles =objRutasXRolesDAO.findAllPorRol(intSecuenciaRol);
		
		//Elimina los roles en la entidad RutasXRoles
		if(!ObjectUtils.isEmpty(lsRutasXRoles)) {
			for(RutasXRoles objRutasXRoles:lsRutasXRoles) {
				objIRutasXRolesDAO.delete(objRutasXRoles);
			}
		}
		
		List<ModuloXRoles> lsModuloXRoles=objModuloXRolesDAO.findModuloPorRol(intSecuenciaRol);
		
		//Elimina los roles en la entidad ModuloXRoles
		if(!ObjectUtils.isEmpty(lsModuloXRoles)) {
			for(ModuloXRoles objModuloXRoles:lsModuloXRoles) {
				objIModuloXRolesDAO.delete(objModuloXRoles);
			}
		}
		
		//Elimina el rol
		objIRolesDAO.delete(objRoles.get());

	}


	@Override
	public RolesDTO consultarRolId(Integer intSecuenciaRol) throws BOException {
		
		//Valida que el campo intSecuenciaRol sea requerida.
		if (ObjectUtils.isEmpty(intSecuenciaRol)) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaRol"});
		
		Optional<Roles> objRoles=objIRolesDAO.findById(intSecuenciaRol);
		
		//Valida que exista el rol.
		if (!objRoles.isPresent()) 
			throw new BOException("ven.warn.campoNoExiste", new Object[] { "ven.campos.secuenciaRol"});
		
		//Valida que exista el rol.
		if ("N".equalsIgnoreCase(objRoles.get().getEsActivo())) 
			throw new BOException("ven.warn.campoInactivo", new Object[] { "ven.campos.secuenciaRol"});
		
		List<ModuloXRoles> lsModuloXRoles= objModuloXRolesDAO.findModuloPorRol(intSecuenciaRol);
		
		RolesDTO objRolesDTO=new RolesDTO();
		objRolesDTO.setSecuenciaRol(intSecuenciaRol);
		objRolesDTO.setNombre(objRoles.get().getNombre());
		objRolesDTO.setSecuenciaModulo(lsModuloXRoles.get(0).getModuloXRolesCPK().getSecuenciaModulo());
		
		return objRolesDTO;
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class})
	public void actualizarRol(RolesDTO objRolesDTO,String strUsuario) throws BOException {
		
		//Valida que objRolesDTO.getSecuenciaRol() sea requerida.
		if (ObjectUtils.isEmpty(objRolesDTO.getSecuenciaRol())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaRol"});
		
		//Valida que el campo objRolesDTO.getNombre() sea requerida.
		if (ObjectUtils.isEmpty(objRolesDTO.getNombre())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.nombre"});
		
		//Valida que el campo intSecuenciaRol sea requerida.
		if (ObjectUtils.isEmpty(objRolesDTO.getSecuenciaModulo())) 
			throw new BOException("ven.warn.campoObligatorio", new Object[] { "ven.campos.secuenciaModulo"});
		
		Optional<Roles> objRoles=objIRolesDAO.findById(objRolesDTO.getSecuenciaRol());
		
		//Valida que exista el rol.
		if (!objRoles.isPresent()) 
			throw new BOException("ven.warn.campoNoExiste", new Object[] { "ven.campos.secuenciaRol"});
		
		//Valida el rol este activo.
		if ("N".equalsIgnoreCase(objRoles.get().getEsActivo())) 
			throw new BOException("ven.warn.campoInactivo", new Object[] { "ven.campos.secuenciaRol"});
		
		Optional<Modulos> objModulos=objModulosDAO.find(objRolesDTO.getSecuenciaModulo());
		
		//Valida que exista el modulo.
		if (!objModulos.isPresent()) 
			throw new BOException("ven.warn.campoNoExiste", new Object[] { "ven.campos.secuenciaModulo"});
		
		//Valida que este activo el modulo.
		if ("N".equalsIgnoreCase(objModulos.get().getEsActivo())) 
			throw new BOException("ven.warn.campoInactivo", new Object[] { "ven.campos.secuenciaModulo"});
		
		if(!objRoles.get().getNombre().equalsIgnoreCase(objRolesDTO.getNombre())) {
			Boolean booExiste=objRolesDAO.consultarRolesPorNombre(objRolesDTO.getNombre());
			
			//Valida si existe el rol
			if(booExiste)
				throw new BOException("ven.warn.nombreRolExiste");
		}
			
		Optional<ModuloXRoles> objModuloXRoles= objModuloXRolesDAO.find(new ModuloXRolesCPK(objRolesDTO.getSecuenciaRol(),objRolesDTO.getSecuenciaModulo()));
		
		if (!objModuloXRoles.isPresent()) {
			ModuloXRoles objModuloXRolesCrear=new ModuloXRoles();
			objModuloXRolesCrear.setModuloXRolesCPK(new ModuloXRolesCPK(objRolesDTO.getSecuenciaRol(),objRolesDTO.getSecuenciaModulo()));
			objModuloXRolesCrear.setEsActivo("S");
			objModuloXRolesCrear.setUsuarioIngreso(strUsuario);
			objModuloXRolesCrear.setFechaIngreso(new Date());
			objModuloXRolesDAO.persist(objModuloXRolesCrear);
		}else if("N".equalsIgnoreCase(objModuloXRoles.get().getEsActivo())) {
			objModuloXRoles.get().setEsActivo("S");
			objModuloXRoles.get().setUsuarioActualizacion(strUsuario);
			objModuloXRoles.get().setFechaModificacion(new Date());
			objModuloXRolesDAO.update(objModuloXRoles.get());
		}
		
		List<ModuloXRoles> lsModuloXRoles= objModuloXRolesDAO.findModuloPorRolExc(objRolesDTO.getSecuenciaRol(),objRolesDTO.getSecuenciaModulo());
		
		//Elimina los otros roles ya que un modulo solo puede tener un solo rol asignado
		if (!ObjectUtils.isEmpty(lsModuloXRoles)) {
			for(ModuloXRoles objModXRoles:lsModuloXRoles) {
				objIModuloXRolesDAO.delete(objModXRoles);
			}
		}
		
		objRoles.get().setNombre(objRolesDTO.getNombre().toUpperCase());
		objIRolesDAO.save(objRoles.get());
			
		
	}


}
