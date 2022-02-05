package com.trabajodegrado.freshfruitusuarios.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitusuarios.modelos.Roles;

@Repository
public interface RolesRepositorio extends JpaRepository<Roles, Integer>{
	 public Optional<Roles> findByCodigo(String codigo);
 }