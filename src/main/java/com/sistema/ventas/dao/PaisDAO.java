package com.sistema.ventas.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.sistema.ventas.model.Pais;

import lombok.NonNull;

@Service
public class PaisDAO extends BaseDAO<Pais,Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected PaisDAO() {
		super(Pais.class);
	}

	@Override
	public void persist(Pais t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Pais t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public Optional<Pais> find(@NonNull Integer id) {
		return super.find(id);
	}

	/*
	 * Consulta todos los paises
	 * 
	 * @author Bryan Zamora
	 * @return
	 */
	public List<Pais> findAll() {
		try {
			return em.createQuery(
					"SELECT p " +
					"FROM Pais p " +
					"WHERE p.esActivo='S'"+
					" ORDER BY p.nombre",Pais.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
