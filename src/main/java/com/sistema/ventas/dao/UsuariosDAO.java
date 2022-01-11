package com.sistema.ventas.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sistema.ventas.dto.ConsultarUsuarioDTO;
import com.sistema.ventas.model.Usuarios;
import com.sistema.ventas.util.StringUtil;

@Service
public class UsuariosDAO extends BaseDAO<Usuarios, Integer>{

	@Autowired
	private RolesDAO objRoles;
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected UsuariosDAO() {
		super(Usuarios.class);
	}

	@Override
	public void persist(Usuarios t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Usuarios t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public void remove(Usuarios t) throws PersistenceException {
		super.remove(t);
	}

	
	@Override
	public Optional<Usuarios> find(@NonNull Integer id) {
		return super.find(id);
	}
	
	/*
	 * Consulta los usuarios del sistema
	 * 
	 * @Author: Bryan Zamora
	 * @Param: strUsuario
	 * @Return
	 */
	public Usuarios  consultarUsuarioSistema(String strUsuario) {
		try {	
			return em.createQuery(
						"SELECT us \n" +
						"  FROM Usuarios us \n" +
						"  WHERE us.usuario=:usuario \n" +
						"  AND us.esActivo = 'S'",Usuarios.class)
						.setParameter("usuario",strUsuario.toUpperCase())
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/*
	 * Consulta los usuarios del sistema 
	 * 
	 * @Author: Bryan Zamora
	 * @Param intPage
	 * @Param intPerPage
	 * @Param strCedulaCodigoUsuario
	 * @Param strEstado
	 * @Return
	 */
	@SuppressWarnings("unchecked")
	public List<ConsultarUsuarioDTO>  consultarUsuarioSistema(Integer intPage, Integer intPerPage, String strCedulaCodigoUsuario, String strEstado, String strUser) {
		
		StringBuilder strJPQL = new StringBuilder();
		
		try {	
			
			strJPQL.append(" SELECT u.secuenciaUsuario as secuenciaUsuario, ");
			strJPQL.append(" 		per.numeroIdentificacion as numeroIdentificacion, ");
			strJPQL.append("    	per.primerApellido as primerApellido,");
			strJPQL.append("    	per.segundoApellido as segundoApellido,");
			strJPQL.append("    	per.primerNombre as primerNombre,");
			strJPQL.append("    	per.segundoNombre as segundoNombre,");
			strJPQL.append("    	per.email as email,");
			strJPQL.append("    	u.usuario as usuario,");
			strJPQL.append("    	u.esActivo as estado");
			strJPQL.append(" FROM 	Usuarios u");
			strJPQL.append(" 	JOIN 	u.personas per");
			strJPQL.append(" WHERE u.esActivo is not null");
			
			if(!ObjectUtils.isEmpty(strCedulaCodigoUsuario))
				strJPQL.append(" AND 	(per.numeroIdentificacion=:valor or u.usuario=:valor)");
			
			if("ACTIVO".equalsIgnoreCase(strEstado))
				strJPQL.append(" AND u.esActivo ='S'");
			else if("INACTIVO".equalsIgnoreCase(strEstado))
				strJPQL.append(" AND u.esActivo ='N'");
			
			strJPQL.append(" ORDER BY primerApellido,segundoApellido,primerNombre,segundoNombre ASC");
			
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createQuery(strJPQL.toString(), Tuple.class);
			
			if(!ObjectUtils.isEmpty(strCedulaCodigoUsuario))
				query.setParameter("valor",strCedulaCodigoUsuario);
			
			query.setFirstResult(intPage * intPerPage - intPerPage).setMaxResults(intPerPage);
			
			return query	
					.getResultList()
					.stream()
					.map(tuple -> {return ConsultarUsuarioDTO.builder()
					.secuenciaUsuario(tuple.get("secuenciaUsuario",Number.class).intValue())
					.numeroIdentificacion(tuple.get("numeroIdentificacion",String.class))
					.nombresCompletos(StringUtil.concatenarApellidosNombres(tuple.get("primerApellido",String.class), tuple.get("segundoApellido",String.class), tuple.get("primerNombre",String.class), tuple.get("segundoNombre",String.class)))
					.usuario(tuple.get("usuario",String.class))
					.email(tuple.get("email",String.class))
					.estado(tuple.get("estado")!=null && "S".equalsIgnoreCase(tuple.get("estado",String.class))?true:false)
					.roles(objRoles.consultarRolesUsuario(tuple.get("secuenciaUsuario",Number.class).intValue()))
					.build();})
					.collect(Collectors.toList());
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/*
	 * Consulta los usuarios del sistema 
	 * 
	 * @Author: Bryan Zamora
	 * @Param intPage
	 * @Param intPerPage
	 * @Param strCedulaCodigoUsuario
	 * @Param strEstado
	 * @Return
	 */
	@SuppressWarnings("unchecked")
	public Long  contarConsultarUsuarioSistema(String strCedulaCodigoUsuario, String strEstado, String strUser) {
		StringBuilder strJPQL = new StringBuilder();
		
		try {	
			
			strJPQL.append(" SELECT count(u) ");
			strJPQL.append(" FROM 	Usuarios u");
			strJPQL.append(" 	JOIN 	u.personas per");
			strJPQL.append(" WHERE u.esActivo is not null");
			
			if(!ObjectUtils.isEmpty(strCedulaCodigoUsuario))
				strJPQL.append(" AND 	(per.numeroIdentificacion=:valor or u.usuario=:valor)");
			
			if("ACTIVO".equalsIgnoreCase(strEstado))
				strJPQL.append(" AND u.esActivo ='S'");
			else if("INACTIVO".equalsIgnoreCase(strEstado))
				strJPQL.append(" AND u.esActivo ='N'");
			
			
			
			Query query =  em.createQuery(strJPQL.toString());
			
			if(!ObjectUtils.isEmpty(strCedulaCodigoUsuario))
				query.setParameter("valor",strCedulaCodigoUsuario);
			
			Long lonUsuarios=(Long) query.getSingleResult();
			
			return lonUsuarios;
			
		} catch (NoResultException e) {
			return new Long(0);
		}
	}

	
	/*
	 * Consulta los usuarios por numero de identificacion
	 * 
	 * @Author: Bryan Zamora
	 * @Param numeroIdentificacion
	 * @Return
	 */
	public Usuarios consultarUsuarioSistemaPorCedula(String numeroIdentificacion) {
		try {	
			return em.createQuery(
						"SELECT us \n" +
						"  FROM Usuarios us \n" +
						"  JOIN us.personas pe \n" +
						"  WHERE pe.numeroIdentificacion=:numeroIdentificacion \n" +
						"  AND pe.esActivo = 'S' \n" +
						"  AND us.esActivo = 'S'",Usuarios.class)
						.setParameter("numeroIdentificacion",numeroIdentificacion)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean validarCodigoRepetido(String strUser) {
		
		StringBuilder strJPQL = new StringBuilder();
		try {	
			strJPQL.append(" SELECT count(u) ");
			strJPQL.append(" FROM 	Usuarios u");
			strJPQL.append(" WHERE u.usuario = :user");
			
			Query query =  em.createQuery(strJPQL.toString());
			
			query.setParameter("user",strUser);
			
			Long lonUsuarios=(Long) query.getSingleResult();
			
			if(lonUsuarios==0)
				return true;
			else
				return false;
			
		} catch (NoResultException e) {
			return false;
		}
	}

	public Usuarios consultarUsuarioSistemaPorCorreo(String strCorreo) {
		try {	
			return em.createQuery(
						"SELECT us \n" +
						"  FROM Usuarios us \n" +
						"  WHERE (lower(us.usuario)=:usuario or us.personas.email=:usuario) \n" +
						"  AND us.esActivo = 'S'",Usuarios.class)
						.setParameter("usuario",strCorreo.toLowerCase())
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
