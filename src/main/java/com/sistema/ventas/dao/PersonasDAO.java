package com.sistema.ventas.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sistema.ventas.model.Personas;

@Service
public class PersonasDAO extends BaseDAO<Personas, Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected PersonasDAO() {
		super(Personas.class);
	}

	@Override
	public void persist(Personas t) throws PersistenceException {
		super.persist(t);
	}

	@Override
	public void update(Personas t) throws PersistenceException {
		super.update(t);
	}

	@Override
	public void remove(Personas t) throws PersistenceException {
		super.remove(t);
	}

	
	@Override
	public Optional<Personas> find(@NonNull Integer id) {
		return super.find(id);
	}
	
	/*
	 * Consulta si existe el correo
	 * 
	 * @Author: Bryan Zamora
	 * @Param numeroIdentificacion
	 * @Return
	 */
	public Personas consultarExisteCorreo(String email) {
		try {	
			return em.createQuery(
						"SELECT pe \n" +
						"FROM Personas pe \n" +
						"WHERE pe.email=:email \n" +
						"AND pe.esActivo = 'S'",Personas.class)
						.setParameter("email",email)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/*
	 * Consulta si existe el correo
	 * 
	 * @Author: Bryan Zamora
	 * @Param numeroIdentificacion
	 * @Return
	 */
	public Personas consultarExisteCorreo(String email,Integer intSecuenciaPersona) {
		try {	
			return em.createQuery(
						"SELECT pe \n" +
						"FROM Personas pe \n" +
						"WHERE pe.email=:email \n" +
						"AND pe.esActivo = 'S'"+
						"AND pe.secuenciaPersona != :secuenciaPersona"
						,Personas.class)
						.setParameter("email",email)
						.setParameter("secuenciaPersona",intSecuenciaPersona)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
