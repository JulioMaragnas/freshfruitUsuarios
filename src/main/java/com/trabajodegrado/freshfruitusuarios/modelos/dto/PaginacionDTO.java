package com.trabajodegrado.freshfruitusuarios.modelos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginacionDTO {

    private Integer paginaActual;
    private Integer paginacion;
    

}
