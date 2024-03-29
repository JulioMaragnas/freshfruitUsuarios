package com.trabajodegrado.freshfruitusuarios.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

	@Table(name = "metasusuario", schema="trabajodegrado")
	@Entity
	public class Metasusuario {
		

		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Integer id;
		    private Integer idmeta;
		    private Integer idusuario;
		    private Integer cantidad;
		    private boolean isalcanzada;
		    private boolean isactivo;
		    private boolean isredimido;
		    
		    @OneToOne
			@JoinColumn(name = "idmeta", insertable = false, updatable = false)
			private Metas meta;
		    
}
