package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.UsuarioXRoles;
import com.sistema.ventas.model.UsuarioXRolesCPK;

@Repository
public interface IUsuarioXRolesDAO extends JpaRepository<UsuarioXRoles, UsuarioXRolesCPK>{

}
