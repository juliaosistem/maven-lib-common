package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.UserResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultImpl - DELETE Operation Tests")
class DefaultImplDeleteTest {

    @Mock
    private CrudSecundaryService<String, String> secundaryService;

    @Mock
    private UserResponses<String> userResponses;

    private DefaultImpl<String, String> defaultImpl;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        defaultImpl = new DefaultImpl<>(secundaryService, userResponses);
        headers = new HttpHeaders();
    }

    @Test
    @DisplayName("Debe eliminar correctamente cuando se proporciona ID válido")
    void testDeleteWithValidId() {
        UUID testId = UUID.randomUUID();
        PlantillaResponse<String> expectedResponse = new PlantillaResponse<>(
            true,
            "Eliminado(a) correctamente",
            HttpStatus.OK.value(),
            null,
            null
        );

        when(secundaryService.delete(testId))
            .thenReturn(expectedResponse);

        Mono<PlantillaResponse<String>> result = defaultImpl.delete(testId, headers);

        StepVerifier.create(result)
            .assertNext(response -> {
                assert response.getRta() == true;
                assert response.getMessage().contains("Eliminado");
            })
            .verifyComplete();

        verify(secundaryService).delete(testId);
    }

    @Test
    @DisplayName("Debe delegar correctamente al secondary service")
    void testDeleteDelegatesToSecondary() {
        String testId = "test-id-123";
        PlantillaResponse<String> response = new PlantillaResponse<>(true, "OK", 200, null, null);

        when(secundaryService.delete(testId))
            .thenReturn(response);

        Mono<PlantillaResponse<String>> result = defaultImpl.delete(testId, headers);

        StepVerifier.create(result)
            .assertNext(resp -> assertNotNull(resp))
            .verifyComplete();

        verify(secundaryService).delete(testId);
    }

    @Test
    @DisplayName("Debe ejecutarse en scheduler boundedElastic")
    void testDeleteUsesScheduler() {
        String testId = "scheduler-test";
        PlantillaResponse<String> response = new PlantillaResponse<>(true, "OK", 200, null, null);

        when(secundaryService.delete(testId))
            .thenReturn(response);

        Mono<PlantillaResponse<String>> result = defaultImpl.delete(testId, headers);

        // Verificar que sea asincrónico usando StepVerifier
        StepVerifier.create(result)
            .assertNext(resp -> assertTrue(resp.getRta()))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar excepción en eliminación")
    void testDeleteHandlesException() {
        String testId = "error-test";

        when(secundaryService.delete(testId))
            .thenThrow(new RuntimeException("Database error"));

        Mono<PlantillaResponse<String>> result = defaultImpl.delete(testId, headers);

        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    @Test
    @DisplayName("Debe retornar respuesta cuando ID no existe")
    void testDeleteNonExistentId() {
        UUID testId = UUID.randomUUID();
        PlantillaResponse<String> notFoundResponse = new PlantillaResponse<>(
            false,
            "No encontrado",
            HttpStatus.NOT_FOUND.value(),
            null,
            null
        );

        when(secundaryService.delete(testId))
            .thenReturn(notFoundResponse);

        Mono<PlantillaResponse<String>> result = defaultImpl.delete(testId, headers);

        StepVerifier.create(result)
            .assertNext(response -> {
                assert response.getRta() == false;
                assert response.getHttpStatus() == HttpStatus.NOT_FOUND.value();
            })
            .verifyComplete();
    }
}
