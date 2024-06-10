package com.common.lib.api.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialRepresentationDTO {

    @JsonProperty("value")
    private  String password;
    @JsonProperty("temporary")
    private boolean temporary;

    @JsonProperty("type")
    private String type = "password";
}
