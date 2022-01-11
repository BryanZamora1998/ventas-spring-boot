package com.sistema.ventas.dao;

import java.math.BigInteger;
import java.util.ArrayList;
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

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sistema.ventas.dto.ConsultarRolesDTO;
import com.sistema.ventas.dto.ConsultarRolesRutaUsuarioDTO;
import com.sistema.ventas.model.Roles;

@Service
public class RolesDAO extends BaseDAO<Roles, Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected RolesDAO() {
		super(Roles.class);
	}

	@Override
	public void persist(Roles t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Roles t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Roles> find(@NonNull Integer id) {
		return super.find(id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ConsultarRolesDTO> consultarRolesUsuario(Integer intSecuenciaUsuario) {
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select r.secuencia_rol as secuenciaRol, r.nombre as nombre ,COALESCE(ru.secuencia_rol,'N') as esSelect ");
			strJPQLBase.append("from tbl_roles r ");
			strJPQLBase.append("    LEFT  JOIN  tbl_usuario_x_roles ru ");
			strJPQLBase.append("     	on 	  ru.secuencia_rol=r.secuencia_rol");
			strJPQLBase.append("		and   ru.es_activo='S' ");
			strJPQLBase.append("		and   ru.secuencia_usuario=:secuenciaUsuario ");
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQLBase.toString(), Tuple.class);
			//PARAMETROS
			query.setParameter("secuenciaUsuario", intSecuenciaUsuario);
	
			return query.getResultList().stream()
					.map(tuple -> ConsultarRolesDTO.builder()
					.secuenciaRol(tuple.get("secuenciaRol")!=null?tuple.get("secuenciaRol", Number.class).intValue():null)
					.nombre(tuple.get("nombre", String.class))
					.esSelect("N".equalsIgnoreCase(tuple.get("esSelect", String.class))?false:true)
					.build())
			.distinct()
			.collect(Collectors.toList());
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConsultarRolesDTO> consultarRolesRuta(String strRuta) {
		
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select distinct a.secuencia_rol as secuenciaRol, a.nombre as nombre from tbl_roles a,tbl_rutas_x_roles b,tbl_rutas_url c ");
			strJPQLBase.append("where a.secuencia_rol=b.secuencia_rol ");
			strJPQLBase.append("and b.secuencia_ruta=c.secuencia_ruta ");
			strJPQLBase.append("and c.nombre like :ruta ");
			strJPQLBase.append("and   a.es_activo='S' ");
			strJPQLBase.append("and   b.es_activo='S' ");
			
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQLBase.toString(), Tuple.class);
			query.setParameter("ruta",strRuta.toUpperCase());
	
			return query.getResultList().stream()
					.map(tuple -> ConsultarRolesDTO.builder()
					.secuenciaRol(tuple.get("secuenciaRol")!=null?tuple.get("secuenciaRol", Number.class).intValue():null)
					.nombre(tuple.get("nombre", String.class))
					.build())
			.distinct()
				.collect(Collectors.toList());
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<ConsultarRolesRutaUsuarioDTO> consultarRolesRutaUsuario(Integer intSecuenciaRol,
			Integer intSecuenciaUsuario,Boolean esPrimeraVez,Integer intSecuenciaRuta) {
		
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select distinct a.secuencia_ruta as secuenciaRuta, ");
			strJPQLBase.append("a.description as description, ");
			strJPQLBase.append("a.nombre as nombre, ");
			strJPQLBase.append("b.es_select as esSelect ");
			strJPQLBase.append("from tbl_rutas_url a  ");
			strJPQLBase.append("left join ( ");
			strJPQLBase.append("			select c.es_select, ");
			strJPQLBase.append("				   c.secuencia_ruta, ");
			strJPQLBase.append("				   c.secuencia_rol ");
			strJPQLBase.append("			from tbl_rutas_x_roles c ");
			strJPQLBase.append(" 			where c.es_activo='S' ");
			strJPQLBase.append(" 		   )b ");
			strJPQLBase.append("			on b.secuencia_ruta=a.secuencia_ruta ");
			strJPQLBase.append("			and b.secuencia_rol = :secuenciaRol ");
			
			if(esPrimeraVez) {
				strJPQLBase.append("WHERE a.secuencia_ruta_padre is null");
			}else {
				strJPQLBase.append("WHERE a.secuencia_ruta_padre=:secuenciaPadre");
			}
			
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQLBase.toString(), Tuple.class);
			query.setParameter("secuenciaRol",intSecuenciaRol);
			
			if(!esPrimeraVez)
				query.setParameter("secuenciaPadre",intSecuenciaRuta);
	
			return query.getResultList().stream()
					.map(tuple -> ConsultarRolesRutaUsuarioDTO.builder()
					.secuenciaRuta(tuple.get("secuenciaRuta")!=null?tuple.get("secuenciaRuta", Number.class).intValue():null)
					.nombre(tuple.get("nombre", String.class))
					.description(tuple.get("description", String.class))
					.esSelect(tuple.get("esSelect")!=null && "S".equalsIgnoreCase(tuple.get("esSelect",String.class))?true:false)
					.rutas(consultarRolesRutaUsuario(intSecuenciaRol,
				          intSecuenciaUsuario,false,tuple.get("secuenciaRuta", Number.class).intValue()))
					.build())
			.distinct()
			.collect(Collectors.toList());
		} catch (NoResultException e) {
			return new ArrayList<ConsultarRolesRutaUsuarioDTO>();
		}
	}
	
	public List<ConsultarRolesDTO> consultarRoles(Integer intPerPage, Integer intPage) {
		
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select r.secuencia_rol as secuenciaRol, r.nombre as nombre from tbl_roles r ");
			strJPQLBase.append("where r.es_activo='S' ");
			strJPQLBase.append("ORDER BY nombre ");
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQLBase.toString(), Tuple.class);
			query.setFirstResult(intPage * intPerPage - intPerPage).setMaxResults(intPerPage);
			
			return query.getResultList().stream()
					.map(tuple -> ConsultarRolesDTO.builder()
					.secuenciaRol(tuple.get("secuenciaRol")!=null?tuple.get("secuenciaRol", Number.class).intValue():null)
					.nombre(tuple.get("nombre", String.class))
					.build())
			.distinct()
			.collect(Collectors.toList());
		} catch (NoResultException e) {
			return null;
		}
	}

	public Boolean consultarRolesPorNombre(String strNombre) {
		
		try {
			StringBuilder strJPQLBase = new StringBuilder();
		
			strJPQLBase.append("select count(1) ");
			strJPQLBase.append("from Roles r ");
			strJPQLBase.append("where r.nombre=:nombre ");
			strJPQLBase.append("and r.esActivo='S' ");
			
			Query query = em.createQuery(strJPQLBase.toString());
			query.setParameter("nombre",strNombre.trim().toUpperCase());
			
			Long a=(Long) query.getSingleResult();
			
			if(a.intValue()==0)
				return false;
			else
				return true;
			
		} catch (NoResultException e) {
			return false;
		}
	}

	public List<ConsultarRolesDTO> consultarRolesUsuarioSesion(Integer intSecuenciaUsuario) {
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select r.secuencia_rol as secuenciaRol, r.nombre as nombre ,COALESCE(ru.secuencia_rol,'N') as esSelect ");
			strJPQLBase.append("from tbl_roles r ");
			strJPQLBase.append("     JOIN  tbl_usuario_x_roles ru ");
			strJPQLBase.append("     	on 	  ru.secuencia_rol=r.secuencia_rol");
			strJPQLBase.append("		and   ru.es_activo='S' ");
			strJPQLBase.append("		and   ru.secuencia_usuario=:secuenciaUsuario ");
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQLBase.toString(), Tuple.class);
			//PARAMETROS
			query.setParameter("secuenciaUsuario", intSecuenciaUsuario);
	
			return query.getResultList().stream()
					.map(tuple -> ConsultarRolesDTO.builder()
					.secuenciaRol(tuple.get("secuenciaRol")!=null?tuple.get("secuenciaRol", Number.class).intValue():null)
					.nombre(tuple.get("nombre", String.class))
					.esSelect("N".equalsIgnoreCase(tuple.get("esSelect", String.class))?false:true)
					.build())
			.distinct()
			.collect(Collectors.toList());
		} catch (NoResultException e) {
			return null;
		}
	}

	public Integer countConsultarRoles() {
		
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append("select count(1) from tbl_roles r ");
			strJPQLBase.append("where r.es_activo='S' ");
			strJPQLBase.append("ORDER BY nombre ");
			Query query =  em.createNativeQuery(strJPQLBase.toString());
		
			BigInteger intCount=(BigInteger) query.getSingleResult();
			
			return intCount.intValue() ;
			
		} catch (NoResultException e) {
			return 0;
		}
	}
}
