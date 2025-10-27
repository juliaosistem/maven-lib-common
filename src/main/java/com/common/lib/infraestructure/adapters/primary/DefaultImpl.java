package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.RequestHeaders;
import com.common.lib.utils.Responses;
import com.common.lib.utils.UserResponses;
import com.common.lib.utils.enums.ResponseType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.util.Map;
import java.util.function.Function;


public class DefaultImpl<RES, RQ, I> implements CrudPrimaryService<RES, RQ, I> {

    private final CrudSecundaryService<RES, RQ, I> secondary;
    private final BusinessClient businessClient;
    private final Function<RQ, Integer> idBusinessExtractor;
    protected final UserResponses<RES> userResponses;


    public DefaultImpl(CrudSecundaryService<RES, RQ, I> secondary, UserResponses<RES> userResponses) {
        this(secondary, null, null, userResponses);
    }

    public DefaultImpl(CrudSecundaryService<RES, RQ, I> secondary,
                       BusinessClient businessClient,
                       Function<RQ, Integer> idBusinessExtractor, UserResponses<RES> userResponses) {
        this.secondary = secondary;
        this.businessClient = businessClient;
        this.idBusinessExtractor = idBusinessExtractor;
        this.userResponses = userResponses;
    }


    @Override
    public Mono<PlantillaResponse<RES>> all(HttpHeaders headers,  Map<String, String> filters) {
        RequestHeaders<I> rh = RequestHeaders.from(headers);

        if ( rh.getId()!= null) {
            return Mono.fromCallable(() -> secondary.byId(rh.getId())).subscribeOn(Schedulers.boundedElastic());
        } else if (rh.getidBussines() != null) {
            return Mono.fromCallable(() -> secondary.byIdBusiness(rh.getidBussines())).subscribeOn(Schedulers.boundedElastic());
        } else {
            return Mono.fromCallable(secondary::all).subscribeOn(Schedulers.boundedElastic());
        }
    }


    @Override
    public Mono<PlantillaResponse<RES>> add(RQ request, HttpHeaders headers) {
        var rh = RequestHeaders.from(headers);
        if (rh.getidBussines() == null) {
            return Mono.just(new PlantillaResponse<>(false, "No llegó parámetro idBussines  en los headers", HttpStatus.BAD_REQUEST, null, null));
        }

        if (businessClient == null) {
            return Mono.fromCallable(() -> secondary.add(request, rh.getidBussines()))
                    .subscribeOn(Schedulers.boundedElastic());
        }

        return Mono.fromCallable(() -> businessClient.canStore(headers, null))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(res -> {
                    if (Boolean.TRUE.equals(res.getRta())) {
                        return Mono.fromCallable(() -> secondary.add(request, rh.getidBussines()))
                                .subscribeOn(Schedulers.boundedElastic());
                    } else {
                        return Mono.just(userResponses.buildResponse(ResponseType.ID_BUSSINES_NO_ENCONTRADO.getCode(), null));
                    }
                })
                .onErrorResume(ex -> {
                    // detectar errores de conexión de Feign (RetryableException) u otros
                    Throwable cause = ex;
                    while (cause != null) {
                        if (cause instanceof feign.RetryableException) {
                            userResponses.buildResponse(ResponseType.FEING_BUSSINES_FALLO.getCode(), null);
                            return Mono.just(new PlantillaResponse<>(false,
                                    "El endpoint business no está disponible en este momento",
                                    HttpStatus.SERVICE_UNAVAILABLE, null, null));
                       
                                }
                        cause = cause.getCause();
                    }
                    // fallback genérico
                    return Mono.just(userResponses.buildResponse(ResponseType.FALLO.getCode(), null));
                });
    }

    @Override
    public Mono<PlantillaResponse<RES>> update(RQ request, I id, String topic) {
        return Mono.fromCallable(() -> secondary.update(request)).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<PlantillaResponse<RES>> delete(I id, String topic) {
        return Mono.fromCallable(() -> secondary.delete(id)).subscribeOn(Schedulers.boundedElastic());
    }
}


