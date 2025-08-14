package com.common.lib.api.controller;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.RequestHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controlador CRUD base plug-and-play.
 * Los métodos usan wrappers tipados para headers y filtros.
 */
public class DefaultCrudController<RES, RQ, E, I> {

    protected final CrudPrimaryService<RES, RQ, E, I> primaryService;

    public DefaultCrudController(CrudPrimaryService<RES, RQ, E, I> primaryService) {
        this.primaryService = primaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<PlantillaResponse<RES>> add(
            @RequestBody RQ request,
            @RequestHeader HttpHeaders headers
    ) {
        RequestHeaders rh = RequestHeaders.from(headers);
        if (rh.getId() == null || rh.getId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new PlantillaResponse<>(false, "No llegó parámetro id en los headers", HttpStatus.BAD_REQUEST, null, null));
        }
        PlantillaResponse<RES> res = primaryService.add(request);
        return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
    }

    @GetMapping("/all")
    public ResponseEntity<PlantillaResponse<RES>> all(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) Map<String, String> filters
    ) {
        RequestHeaders rh = RequestHeaders.from(headers);
        // Wrap de filtros para futura compatibilidad (reservado)
        // QueryFilters qf = new QueryFilters(filters);
        PlantillaResponse<RES> res;
        if (rh.getId() != null && !rh.getId().isBlank()) {
            res = primaryService.byId(castId(rh.getId()));
        } else if (rh.getIdbusiness() != null) {
            res = primaryService.byIdBusiness(rh.getIdbusiness());
        } else {
            res = primaryService.all();
        }
        return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
    }

	@GetMapping("/findById/{id}")
	public ResponseEntity<PlantillaResponse<RES>> findById(
			@PathVariable String id,
			@RequestHeader HttpHeaders headers
	) {
		PlantillaResponse<RES> res = primaryService.byId(castId(id));
		return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
	}

	@GetMapping("/all/{idBussines}")
	public ResponseEntity<PlantillaResponse<RES>> allByBusiness(
			@PathVariable("idBussines") Long idBusiness,
			@RequestHeader HttpHeaders headers
	) {
		PlantillaResponse<RES> res = primaryService.byIdBusiness(idBusiness);
		return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
	}

    @PutMapping("/update")
    public Mono<PlantillaResponse<RES>> update(
            @RequestBody RQ request,
            @RequestHeader HttpHeaders headers
    ) {
        RequestHeaders rh = RequestHeaders.from(headers);
        if (rh.getId() == null || rh.getId().isBlank() || rh.getTopic() == null || rh.getTopic().isBlank()) {
            return Mono.just(new PlantillaResponse<>(false, "No llegó parámetro id en los headers", HttpStatus.BAD_REQUEST, null, null));
        }
        return Mono.just(primaryService.update(request));
    }

	@DeleteMapping("/delete")
	public ResponseEntity<PlantillaResponse<RES>> delete(
			@RequestHeader HttpHeaders headers,
			@RequestParam(name = "id", required = false) String idParam
	) {
		RequestHeaders rh = RequestHeaders.from(headers);
		String idValue = (idParam != null && !idParam.isBlank()) ? idParam : rh.getId();
		if (idValue == null || idValue.isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new PlantillaResponse<>(false, "No llegó parámetro id", HttpStatus.BAD_REQUEST, null, null));
		}
		PlantillaResponse<RES> res = primaryService.delete(castId(idValue));
		return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
	}

    @SuppressWarnings("unchecked")
    protected I castId(String id) {
        try {
            Long num = Long.parseLong(id);
            return (I) num;
        } catch (Exception ex) {
            return (I) id;
        }
    }
}


