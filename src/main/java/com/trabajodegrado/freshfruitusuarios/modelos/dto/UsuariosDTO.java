package com.trabajodegrado.freshfruitusuarios.modelos.dto;

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
	public class UsuariosDTO {
		
		    private String usuario;
		    private String clave;
}
