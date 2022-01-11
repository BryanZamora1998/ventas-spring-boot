package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.Personas;

@Repository
public interface IPersonasDAO extends JpaRepository<Personas, Integer>{

}
