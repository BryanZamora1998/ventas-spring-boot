package com.sistema.ventas.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.sistema.ventas.model.Empresas;

import lombok.NonNull;

@Service
public class EmpresasDAO extends BaseDAO<Empresas,Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected EmpresasDAO() {
		super(Empresas.class);
	}

	@Override
	public void persist(Empresas t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Empresas t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Empresas> find(@NonNull Integer id) {
		return super.find(id);
	}
}
