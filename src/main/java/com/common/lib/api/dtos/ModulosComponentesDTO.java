package com.common.lib.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModulosComponentesDTO {

    /**
     * Identificador único de la relación módulo-componente
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * Módulo asociado a los componentes
     */
    @JsonProperty("modulo")
    @NotNull
    private ModuloDTO modulo;

    /**
     * Componente asociado al módulo
     */
    @JsonProperty("componente")
    @NotNull
    private ComponentesDTO componente;

}