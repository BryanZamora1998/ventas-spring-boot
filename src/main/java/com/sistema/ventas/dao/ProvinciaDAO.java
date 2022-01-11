package com.sistema.ventas.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.sistema.ventas.model.Provincia;
import com.sistema.ventas.model.ProvinciaCPK;

import lombok.NonNull;

@Service
public class ProvinciaDAO extends BaseDAO<Provincia,ProvinciaCPK>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected ProvinciaDAO() {
		super(Provincia.class);
	}

	@Override
	public void persist(Provincia t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Provincia t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Provincia> find(@NonNull ProvinciaCPK id) {
		return super.find(id);
	}

}
