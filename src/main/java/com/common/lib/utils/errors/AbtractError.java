package com.common.lib.utils.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitario centralizado de logging y trazabilidad para todos los componentes del sistema.
 * Puede inyectarse directamente (composición) o extenderse (herencia).
 *
 * <p>Métodos disponibles:</p>
 * <ul>
 *   <li>{@link #logInfo(String)} — Información general.</li>
 *   <li>{@link #logError(Throwable)} — Errores con stack trace.</li>
 *   <li>{@link #logWarn(String)} — Advertencias.</li>
 *   <li>{@link #logExecutionTime(String, long)} — Tiempo de ejecución.</li>
 *   <li>{@link #startLogger(String)} — Marca inicio de operación.</li>
 *   <li>{@link #endLogger(String)} — Marca fin y calcula duración.</li>
 *   <li>{@link #traceRequest(String, Object)} — Traza entrada de método.</li>
 *   <li>{@link #traceResponse(String, Object)} — Traza salida de método.</li>
 * </ul>
 */
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AbtractError extends Exception {

    protected transient Logger logger = LogManager.getLogger(getClass());
    private transient Long startTime;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ─── Métodos existentes (sin cambios de firma) ───────────────────────────

    /**
     * Registra un error con su stack trace completo.
     *
     * @param throwable Excepción o error a registrar.
     */
    public void logError(Throwable throwable) {
        logger.error("Error occurred in %%s:%s--%s".formatted(
                getClass().getName(), getLocalizedMessage()),
                getClass().getName() + getLocalizedMessage(), throwable);
    }

    /**
     * Registra un mensaje informativo.
     *
     * @param message Mensaje a registrar.
     */
    public void logInfo(String message) {
        logger.info(message, getClass().getName());
    }

    /**
     * Registra el tiempo de ejecución de una operación crítica.
     *
     * @param context   Nombre de la operación / contexto.
     * @param startTime Marca de tiempo de inicio (System.currentTimeMillis()).
     */
    public void logExecutionTime(String context, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        String formattedDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy : HH:mm:ss"));
        logInfo(context + " - Tiempo de ejecución: " + duration
                + " ms - fecha de ejecucion " + formattedDate);
    }

    // ─── Métodos nuevos ──────────────────────────────────────────────────────

    /**
     * Registra un mensaje de advertencia.
     *
     * @param message Mensaje de advertencia.
     */
    public void logWarn(String message) {
        logger.warn(message, getClass().getName());
    }

    /**
     * Marca el inicio de una operación y guarda el timestamp interno.
     * Usar en conjunto con {@link #endLogger(String)}.
     *
     * @param operation Nombre descriptivo de la operación que inicia.
     */
    public void startLogger(String operation) {
        this.startTime = System.currentTimeMillis();
        logInfo("[INICIO] " + operation + " - timestamp: "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy : HH:mm:ss")));
    }

    /**
     * Marca el fin de una operación, calcula la duración y la registra en minutos y segundos.
     * Requiere haber llamado {@link #startLogger(String)} previamente.
     *
     * @param operation Nombre descriptivo de la operación que finaliza.
     */
    public void endLogger(String operation) {
        if (startTime == null) {
            logWarn("[FIN] " + operation + " - startLogger() no fue ejecutado antes");
            return;
        }
        long durationMs = System.currentTimeMillis() - startTime;
        long minutes = durationMs / 60000;
        long seconds = (durationMs % 60000) / 1000;
        logInfo("[FIN] " + operation + " - duracion: " + durationMs
                + " ms (" + minutes + "m " + seconds + "s)");
        this.startTime = null;
    }

    /**
     * Registra la entrada de un método con su payload serializado de forma segura.
     *
     * @param method  Nombre del método / contexto de negocio.
     * @param payload Datos de entrada a serializar.
     */
    public void traceRequest(String method, Object payload) {
        logInfo("[ENTRADA] " + method + " - request: " + safeStringify(payload));
    }

    /**
     * Registra la salida de un método con su payload serializado de forma segura.
     *
     * @param method  Nombre del método / contexto de negocio.
     * @param payload Datos de salida a serializar.
     */
    public void traceResponse(String method, Object payload) {
        logInfo("[SALIDA] " + method + " - response: " + safeStringify(payload));
    }

    /**
     * Serializa un objeto a JSON de forma segura; si falla devuelve un marcador.
     *
     * @param payload Objeto a serializar.
     * @return Cadena JSON o {@code [payload_no_serializable]}.
     */
    protected String safeStringify(Object payload) {
        if (payload == null) return "null";
        try {
            return OBJECT_MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return "[payload_no_serializable]";
        }
    }
}
