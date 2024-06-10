package com.common.lib.api.dtos.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRepresentationDTO {

    private String id;

    @JsonProperty("createdTimestamp")
    private long createdTimestamp;

    private String username;
    private boolean enabled;
    private boolean totp;

    @JsonProperty("emailVerified")
    private boolean emailVerified;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    private String email;

    @JsonProperty("disableableCredentialTypes")
    private List<String> disableableCredentialTypes;

    @JsonProperty("requiredActions")
    private List<String> requiredActions;

    @JsonProperty("notBefore")
    private int notBefore;

    @JsonProperty("access")
    private accessDTO access;

}