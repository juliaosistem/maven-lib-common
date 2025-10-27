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
public class ModuloDTO {

    /**
     * Identificador único del módulo. Debe ser un número positivo.
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * Nombre del módulo
     */
    @JsonProperty("nombreModulo")
    @NotNull
    private String nombreModulo;

    /**
     * Lista de roles o permisos asociados al módulo. Debe contener al menos un rol.
     */
    @JsonProperty("rolesPermiso")
    @NotNull
    private List<String> rolesPermiso;

}
