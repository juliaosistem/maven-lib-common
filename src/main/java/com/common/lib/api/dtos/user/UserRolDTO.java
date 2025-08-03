package com.common.lib.api.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author daniel juliao
 * @version 1
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    @JsonProperty( "rol")
    private RolDTO rol;
    @NotNull
    @NotBlank
    @NotEmpty
    @JsonProperty( "user")
    private UserDTO user;

}
