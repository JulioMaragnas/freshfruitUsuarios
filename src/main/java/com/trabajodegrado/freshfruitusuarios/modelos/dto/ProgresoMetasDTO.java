package com.trabajodegrado.freshfruitusuarios.modelos.dto;

import java.util.Date;

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
public class ProgresoMetasDTO {

    private Integer idMeta;
    private String  nombreMeta;
    private Date  fechaInicio;
    private Date  fechaFin;
    private Integer  cantidadAlcanzar;
    private Integer  cantidadAlcanzada;
    private Integer  porcentajeAlcanzado;
    
    

}
