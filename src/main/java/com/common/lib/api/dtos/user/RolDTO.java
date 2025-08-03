package com.common.lib.api.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author daniel juliao
 * @version 1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private UUID idRol;

    @NotNull
    @NotBlank
    @NotEmpty
    private String nameRol;

}
