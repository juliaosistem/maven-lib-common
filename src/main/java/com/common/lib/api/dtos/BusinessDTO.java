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
public class BusinessDTO {

    /**
     * Identificador del negocio asociado
     */
    @JsonProperty("idBussines")
    private Integer idBussines;

    /**
     * Nombre del negocio
     */
    @JsonProperty("nombreNegocio")
    private String nombreNegocio;

    /**
     * Número de identificación del negocio
     */
    @JsonProperty("numeroIdentificacionNegocio")
    private String numeroIdentificacionNegocio;

    /**
     * Código del país asociado al negocio
     */
    @JsonProperty("codigoPais")
    private String codigoPais;

    /**
     * Colores asociados al negocio
     */
    @JsonProperty("colores")
    private Object colores;

    /**
     * URL del logo del negocio
     */
    @JsonProperty("logo")
    private String logo;

    /**
     * Palabras clave asociadas al negocio
     */
    @JsonProperty("keyWords")
    private List<String> keyWords;

    /**
     * Descripción del negocio
     */
    @JsonProperty("descripcion")
    private String descripcion;

    /**
     * URL de WhatsApp del negocio
     */
    @JsonProperty("urlWhatssapp")
    private String urlWhatssapp;

    /**
     * Correo electrónico del negocio
     */
    @JsonProperty("email")
    private String email;

    /**
     * Dirección del negocio
     */
    @JsonProperty("direccion")
    private String direccion;

    /**
     * Lenguaje principal del negocio
     */
    @JsonProperty("lenguaje")
    private String lenguaje;

    /**
     * Lista de módulos asociados al negocio
     */
    @JsonProperty("businessModule")
    @NotNull
    private List<BusinessModuleDTO> businessModule;

    /**
     * Lista de productos asociados al negocio
     */
    @JsonProperty("productos")
    private List<ProductoDTO> productos;

    /**
     * Número de teléfono del negocio. Debe ser un número válido de Colombia, Ecuador, Portugal, España o Alemania
     */
    @JsonProperty("telefono")
    @NotNull
    private String telefono;

}
