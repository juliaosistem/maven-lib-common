package com.common.lib.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponse {

    private   UUID id;
    private String proceso;

}
