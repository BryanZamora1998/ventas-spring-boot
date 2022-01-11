package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.ModuloXRoles;
import com.sistema.ventas.model.ModuloXRolesCPK;

@Repository
public interface IModuloXRolesDAO  extends JpaRepository<ModuloXRoles,ModuloXRolesCPK>{

}
