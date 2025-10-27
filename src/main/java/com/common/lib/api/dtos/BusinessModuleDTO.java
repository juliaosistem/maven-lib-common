package com.common.lib.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessModuleDTO {

    /**
     * Identificador único del módulo del negocio
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * Información del negocio al que pertenece este módulo
     */
    @JsonProperty("idBussines")
    private Integer idBussines;

    /**
     * Lista de módulos con sus respectivos componentes
     */
    @JsonProperty("modulosComponentes")
    @NotNull
    private List<ModulosComponentesDTO> modulosComponentes;

}
