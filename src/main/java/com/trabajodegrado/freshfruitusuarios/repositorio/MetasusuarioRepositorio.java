package com.trabajodegrado.freshfruitusuarios.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitusuarios.modelos.Metasusuario;

@Repository
public interface MetasusuarioRepositorio extends JpaRepository<Metasusuario, Integer>{
    public Optional<Metasusuario> findByIdusuarioAndIdmeta(Integer idusuario, Integer idmeta);
    public List<Metasusuario> findByIdusuario(Integer idusuario);
      
 }