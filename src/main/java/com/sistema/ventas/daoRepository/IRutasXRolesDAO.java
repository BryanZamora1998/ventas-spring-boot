package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.RutasXRoles;
import com.sistema.ventas.model.RutasXRolesCPK;

@Repository
public interface IRutasXRolesDAO extends JpaRepository<RutasXRoles, RutasXRolesCPK>{

}
