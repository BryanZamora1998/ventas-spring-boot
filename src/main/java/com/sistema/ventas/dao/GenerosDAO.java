package com.sistema.ventas.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sistema.ventas.model.Generos;

@Service
public class GenerosDAO extends BaseDAO<Generos, Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected GenerosDAO() {
		super(Generos.class);
	}

	@Override
	public void persist(Generos t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Generos t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Generos> find(@NonNull Integer id) {
		return super.find(id);
	}

	/*
	 * Consulta los generos 
	 * 
	 * @Author: Bryan Zamora
	 * @Return
	 */
	public List<Generos> findAll() {
		try {
			return em.createQuery(
					"SELECT g " +
					"FROM Generos g " +
					"WHERE g.esActivo='S' ", Generos.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
