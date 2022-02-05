package com.trabajodegrado.freshfruitusuarios.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitusuarios.modelos.Usuarios;

@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuarios, Integer>{
    public Optional<Usuarios> findByNombreusuarioAndClave(String nombreusuario, String cave);
    public Optional<Usuarios> findByNombreusuario(String nombreusuario);
    public Optional<Usuarios> findByCorreoelectronico(String correo);
    public Page<Usuarios> findByIdestado(Integer idestado, Pageable page);
    public List<Usuarios> findByIdrol(Integer idrol);
 }