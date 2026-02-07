package com.common.lib.api.dtos.response;

import com.common.lib.api.dtos.request.RegisterUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private Object keycloakToken;
    private String businessToken;
}
