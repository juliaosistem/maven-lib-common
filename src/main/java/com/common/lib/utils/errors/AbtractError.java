package com.common.lib.utils.errors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Component
public  class AbtractError extends  Exception  {
    protected transient Logger logger = LogManager.getLogger(getClass());

    public void logError(Throwable throwable ) {
        logger.error("Error occurred in %%s:%s--%s".formatted(getClass().getName(), getLocalizedMessage()), getClass().getName() + getLocalizedMessage() , throwable);
    }

    public void logInfo(String message) {
        logger.info(message, getClass().getName());
    }

    public void logExecutionTime(String context, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy : HH:mm:ss"));
        logInfo(context + " - Tiempo de ejecución: " + duration + " ms - fecha de ejecucion " + formattedDate);
    }

}
