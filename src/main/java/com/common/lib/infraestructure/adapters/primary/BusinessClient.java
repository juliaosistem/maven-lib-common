package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.api.dtos.BusinessDTO;
import com.common.lib.utils.PlantillaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "business-client", url = "https://2f14df16-d9cc-45e9-95a1-341be6a24311.mock.pstmn.io/bussines")
public interface BusinessClient {

    @GetMapping("/all")
    PlantillaResponse<BusinessDTO> canStore(@RequestHeader HttpHeaders headers,
                                           @RequestParam(required = false) Map<String, String> filters);
}
