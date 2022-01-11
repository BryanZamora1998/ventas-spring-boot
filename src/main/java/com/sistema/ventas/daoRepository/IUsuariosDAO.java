package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.Usuarios;

@Repository
public interface IUsuariosDAO extends JpaRepository<Usuarios, Integer>{

}
