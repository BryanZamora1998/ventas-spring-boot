package com.sistema.ventas.daoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.ventas.model.ModulosXUsuario;
import com.sistema.ventas.model.ModulosXUsuarioCPK;

@Repository
public interface IModulosXUsuarioDAO extends JpaRepository<ModulosXUsuario,ModulosXUsuarioCPK>{

}
