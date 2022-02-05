package com.trabajodegrado.freshfruitusuarios.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor

	@Table(name = "roles", schema="trabajodegrado")
	@Entity
	public class Roles {
		
		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Integer id;
		    private String descripcion;
		    private String codigo;
		   
}
