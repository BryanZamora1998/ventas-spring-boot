package com.sistema.ventas.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.stereotype.Service;

import com.sistema.ventas.model.ModuloXRoles;
import com.sistema.ventas.model.ModuloXRolesCPK;
import com.sistema.ventas.model.ModulosXUsuario;
import com.sistema.ventas.model.ModulosXUsuarioCPK;

import lombok.NonNull;

@Service
public class ModulosXUsuarioDAO extends BaseDAO<ModulosXUsuario,ModulosXUsuarioCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected ModulosXUsuarioDAO() {
		super(ModulosXUsuario.class);
	}

	@Override
	public void persist(ModulosXUsuario t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(ModulosXUsuario t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<ModulosXUsuario> find(@NonNull ModulosXUsuarioCPK id) {
		return super.find(id);
	}
	
	
	public List<ModulosXUsuario> findModuloPorUsuario(Integer intSecuenciaUsuario) {
		try {	
			return em.createQuery(
						"SELECT t " +
						"  FROM ModulosXUsuario t " +
						"  WHERE t.modulosXUsuarioCPK.secuenciaUsuario = :secuenciaUsuario ",ModulosXUsuario.class)
						.setParameter("secuenciaUsuario", intSecuenciaUsuario)
						.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<ModuloXRoles> findModuloPorRolExc(Integer intSecuenciaRol,Integer intSecuenciaModulo) {
		try {	
			return em.createQuery(
						"SELECT t \n" +
						"  FROM ModuloXRoles t \n" +
						"  WHERE t.moduloXRolesCPK.secuenciaRol = :secuenciaRol"+
						"  AND t.moduloXRolesCPK.secuenciaModulo != :secuenciaModulo",ModuloXRoles.class)
						.setParameter("secuenciaRol", intSecuenciaRol)
						.setParameter("secuenciaModulo", intSecuenciaModulo)
						.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Boolean consultaPermisoModulo(String usuario,String nemonico) {
		
		try {
			StringBuilder strJPQLBase = new StringBuilder();
			strJPQLBase.append(" select count(mu.secuencia_modulo) ");
			strJPQLBase.append(" from tbl_modulos_x_usuario mu ");
			strJPQLBase.append("	join tbl_usuarios usu ");
			strJPQLBase.append("		on usu.secuencia_usuario=mu.secuencia_usuario ");
			strJPQLBase.append("	join tbl_modulos mo ");
			strJPQLBase.append(" 		on mo.secuencia_modulo=mu.secuencia_modulo ");
			strJPQLBase.append(" where usu.usuario=:usuario");
			strJPQLBase.append(" and mo.mnemonico=:mnemonico ");
			strJPQLBase.append(" and mu.es_activo='S' ");
			
			@SuppressWarnings("unchecked")
			Query query = em.createNativeQuery(strJPQLBase.toString());
	
			query.setParameter("usuario", usuario);
			query.setParameter("mnemonico", nemonico);
			BigInteger count=(BigInteger) query.getSingleResult();
			
			return count.intValue()>0?true:false;
			
		} catch (NoResultException e) {
			return false;
		}
	}

}