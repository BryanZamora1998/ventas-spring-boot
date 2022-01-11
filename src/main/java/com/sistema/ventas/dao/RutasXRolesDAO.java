package com.sistema.ventas.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sistema.ventas.model.RutasXRoles;
import com.sistema.ventas.model.RutasXRolesCPK;

@Service
public class RutasXRolesDAO extends BaseDAO<RutasXRoles, RutasXRolesCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected RutasXRolesDAO() {
		super(RutasXRoles.class);
	}

	@Override
	public void persist(RutasXRoles t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(RutasXRoles t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<RutasXRoles> find(@NonNull RutasXRolesCPK id) {
		return super.find(id);
	}

	/*
	 * Consulta los tipos de identificacion 
	 * 
	 * @Author: Bryan Zamora
	 * @Return
	 */
	public List<RutasXRoles> findAll() {
		try {	
			return em.createQuery(
						"SELECT t \n" +
						"  FROM RutasXRoles t \n" +
						"  WHERE t.esActivo = 'S' ",RutasXRoles.class)
						.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<RutasXRoles> findAllPorRol(Integer secuenciaRol) {
		try {	
			return em.createQuery(
						"SELECT t \n" +
						"  FROM RutasXRoles t \n" +
						"  WHERE t.rutasXRolesCPK.secuenciaRol = :secuenciaRol",RutasXRoles.class)
						.setParameter("secuenciaRol", secuenciaRol)
						.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	

}
