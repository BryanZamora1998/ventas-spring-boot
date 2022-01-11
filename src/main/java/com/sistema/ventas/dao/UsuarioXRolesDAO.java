package com.sistema.ventas.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sistema.ventas.model.UsuarioXRoles;
import com.sistema.ventas.model.UsuarioXRolesCPK;

@Service
public class UsuarioXRolesDAO extends BaseDAO<UsuarioXRoles,UsuarioXRolesCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected UsuarioXRolesDAO() {
		super(UsuarioXRoles.class);
	}

	@Override
	public void persist(UsuarioXRoles t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(UsuarioXRoles t) throws PersistenceException {
		super.update(t);
	}
	
	@Override
	public void remove(UsuarioXRoles t) throws PersistenceException {
		super.remove(t);
	}

	@Override
	public Optional<UsuarioXRoles> find(@NonNull UsuarioXRolesCPK id) {
		return super.find(id);
	}

	public List<UsuarioXRoles> findRolAllPorUsuario(Integer intSecuenciaUsuario) {
		try {	
			return em.createQuery(
						"SELECT t \n" +
						"  FROM UsuarioXRoles t \n" +
						"  WHERE t.esActivo = 'S' "+
						"  AND   t.usuariosXRolesCPK.secuenciaUsuario = :secuenciaUsuario",UsuarioXRoles.class)
						.setParameter("secuenciaUsuario", intSecuenciaUsuario)
						.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<UsuarioXRoles> findRolAllPorRol(Integer intSecuenciaRol) {
		try {	
			return em.createQuery(
						"SELECT t \n" +
						"  FROM UsuarioXRoles t \n" +
						"  WHERE t.usuariosXRolesCPK.secuenciaRol = :secuenciaRol",UsuarioXRoles.class)
						.setParameter("secuenciaRol", intSecuenciaRol)
						.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
