package com.common.lib.api.dtos.request;

import com.common.lib.api.dtos.user.KeycloakBuilderDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.common.lib.api.dtos.user.DatesUserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterUserDTO {

        @JsonProperty("idBussines")
        private Long idBussines;
        @JsonProperty("estado")
        private String estado;
        @JsonProperty("id")
        private UUID id;
        @JsonProperty("email")
        private String email;

        @JsonProperty("password")
        private String password;

        @JsonProperty("datesUser")
        private DatesUserDTO datesUser;

        @JsonProperty("token")
        private String token;

        @JsonProperty("keyCloak")
        private KeycloakBuilderDTO keyCloak;
}
