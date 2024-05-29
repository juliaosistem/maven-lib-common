package com.juliaosystem.api.dtos.user;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeycloakBuilderDTO {

    private String realm;
    private String clientId;

}
