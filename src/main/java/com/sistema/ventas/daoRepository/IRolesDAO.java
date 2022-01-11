package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.Roles;

@Repository
public interface IRolesDAO  extends JpaRepository<Roles, Integer>{

}
