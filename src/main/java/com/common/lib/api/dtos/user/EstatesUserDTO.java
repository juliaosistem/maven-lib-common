package com.common.lib.api.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author daniel juliao
 * @version 1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstatesUserDTO {
    private  Integer idStateUser;

    @NotEmpty
    @NotNull
    @NotBlank
    @Min(3)
    @JsonProperty( "nameState")
    private String nameState;
    @JsonProperty( "description")
    private String description;
}
