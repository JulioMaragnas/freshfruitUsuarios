package com.trabajodegrado.freshfruitusuarios.repositorio;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitusuarios.modelos.Metas;


@Repository
public interface MetasRepositorio extends JpaRepository<Metas, Integer>{
	public Optional<Metas> findByIdproducto(Integer idproducto);
	
	public List<Metas>findByFechainicioLessThanEqualAndFechafinGreaterThanEqual(Date fecha1, Date fecha2);

 }