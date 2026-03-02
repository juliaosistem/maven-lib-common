package com.common.lib.infraestructure.adapters.secundary;

import com.common.lib.api.mappers.GenericMapper;
import com.common.lib.api.mappers.MapperConRequest;
import com.common.lib.infraestructure.repository.DefaultRepository;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.UserResponses;
import com.common.lib.utils.enums.ResponseType;
import com.common.lib.utils.errors.AbtractError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAdapterRepository - DELETE Operation Tests")
class DefaultAdapterRepositoryDeleteTest {

    @Mock
    private GenericMapper<String, String, String> mapper;

    @Mock
    private AbtractError abstractError;

    @Mock
    private UserResponses<String> userResponses;

    @Mock
    private DefaultRepository<String, Object> defaultRepository;

    @Mock
    private MapperConRequest<String, String, String> mapperConRequest;

    private DefaultAdapterRepository<String, String, String> adapter;

    @BeforeEach
    void setUp() {
        adapter = new DefaultAdapterRepository<>(
            mapper,
            abstractError,
            userResponses,
            String.class,
            String.class,
            defaultRepository,
            mapperConRequest
        );
    }

    @Test
    @DisplayName("Debe eliminar exitosamente entidad por ID")
    void testDeleteSuccessfully() {
        UUID testId = UUID.randomUUID();
        PlantillaResponse<String> successResponse = new PlantillaResponse<>(
            true, "Eliminado(a) correctamente", 200, null, null
        );

        when(defaultRepository.deleteByIdSafe(testId)).thenReturn(true);
        when(userResponses.buildResponse(ResponseType.DELETED.getCode(), null))
            .thenReturn(successResponse);
        doNothing().when(abstractError).logInfo(any());
        doNothing().when(abstractError).logExecutionTime(any(), any(Long.class));

        PlantillaResponse<String> result = adapter.delete(testId);

        assertTrue(result.getRta());
        assertEquals(200, result.getHttpStatus());
    }

    @Test
    @DisplayName("Debe retornar error cuando entidad no existe")
    void testDeleteNonExistentEntity() {
        UUID testId = UUID.randomUUID();
        PlantillaResponse<String> notFoundResponse = new PlantillaResponse<>(
            false, "No encontrado", 404, null, null
        );

        when(defaultRepository.deleteByIdSafe(testId)).thenReturn(false);
        when(userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null))
            .thenReturn(notFoundResponse);
        doNothing().when(abstractError).logInfo(any());
        doNothing().when(abstractError).logExecutionTime(any(), any(Long.class));

        PlantillaResponse<String> result = adapter.delete(testId);

        assertFalse(result.getRta());
        assertEquals(404, result.getHttpStatus());
    }

    @Test
    @DisplayName("Debe loguear información en cada operación")
    void testDeleteLogsInfo() {
        String testId = "test-id-123";
        PlantillaResponse<String> response = new PlantillaResponse<>(
            true, "Deleted", 200, null, null
        );

        when(defaultRepository.deleteByIdSafe(testId)).thenReturn(true);
        when(userResponses.buildResponse(ResponseType.DELETED.getCode(), null))
            .thenReturn(response);
        doNothing().when(abstractError).logInfo(any());
        doNothing().when(abstractError).logExecutionTime(any(), any(Long.class));

        adapter.delete(testId);

        verify(abstractError).logInfo("DefaultAdapter.delete() id =" + testId);
    }

    @Test
    @DisplayName("Debe manejar excepción durante eliminación")
    void testDeleteHandlesException() {
        String testId = "error-test";
        PlantillaResponse<String> errorResponse = new PlantillaResponse<>(
            false, "Error", 500, null, null
        );

        when(defaultRepository.deleteByIdSafe(testId))
            .thenThrow(new RuntimeException("DB error"));
        when(userResponses.buildResponse(ResponseType.FALLO.getCode(), null))
            .thenReturn(errorResponse);
        doNothing().when(abstractError).logInfo(any());
        doNothing().when(abstractError).logError(any(Exception.class));
        doNothing().when(abstractError).logExecutionTime(any(), any(Long.class));

        PlantillaResponse<String> result = adapter.delete(testId);

        assertFalse(result.getRta());
        assertEquals(500, result.getHttpStatus());
    }

    @Test
    @DisplayName("Debe permitir eliminación con diferentes tipos de ID")
    void testDeleteWithDifferentIdTypes() {
        String stringId = "string-id-value";
        PlantillaResponse<String> response = new PlantillaResponse<>(
            true, "Deleted", 200, null, null
        );

        when(defaultRepository.deleteByIdSafe(stringId)).thenReturn(true);
        when(userResponses.buildResponse(ResponseType.DELETED.getCode(), null))
            .thenReturn(response);
        doNothing().when(abstractError).logInfo(any());
        doNothing().when(abstractError).logExecutionTime(any(), any(Long.class));

        PlantillaResponse<String> result = adapter.delete(stringId);
        assertTrue(result.getRta());
    }

    @Test
    @DisplayName("Debe retornar respuesta válida después de eliminación")
    void testDeleteReturnsValidResponse() {
        UUID testId = UUID.randomUUID();
        PlantillaResponse<String> response = new PlantillaResponse<>(
            true, "Eliminado(a)", 200, null, null
        );

        when(defaultRepository.deleteByIdSafe(testId)).thenReturn(true);
        when(userResponses.buildResponse(ResponseType.DELETED.getCode(), null))
            .thenReturn(response);
        doNothing().when(abstractError).logInfo(any());
        doNothing().when(abstractError).logExecutionTime(any(), any(Long.class));

        PlantillaResponse<String> result = adapter.delete(testId);

        assertNotNull(result);
        assertTrue(result.getRta());
        assertEquals(200, result.getHttpStatus());
    }
}
