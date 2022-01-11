package com.sistema.ventas.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.sistema.ventas.model.ModuloXRoles;
import com.sistema.ventas.model.ModuloXRolesCPK;

import lombok.NonNull;

@Service
public class ModuloXRolesDAO extends BaseDAO<ModuloXRoles,ModuloXRolesCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected ModuloXRolesDAO() {
		super(ModuloXRoles.class);
	}

	@Override
	public void persist(ModuloXRoles t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(ModuloXRoles t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<ModuloXRoles> find(@NonNull ModuloXRolesCPK id) {
		return super.find(id);
	}
	
	
	public List<ModuloXRoles> findModuloPorRol(Integer intSecuenciaRol) {
		try {	
			return em.createQuery(
						"SELECT t \n" +
						"  FROM ModuloXRoles t \n" +
						"  WHERE t.moduloXRolesCPK.secuenciaRol = :secuenciaRol",ModuloXRoles.class)
						.setParameter("secuenciaRol", intSecuenciaRol)
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

}