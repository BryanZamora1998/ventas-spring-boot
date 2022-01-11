package com.sistema.ventas.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.sistema.ventas.model.Ciudad;
import com.sistema.ventas.model.CiudadCPK;

import lombok.NonNull;

@Service
public class CiudadDAO extends BaseDAO<Ciudad,CiudadCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected CiudadDAO() {
		super(Ciudad.class);
	}

	@Override
	public void persist(Ciudad t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Ciudad t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Ciudad> find(@NonNull CiudadCPK id) {
		return super.find(id);
	}
}
