package juliaosystem.comomlib.utils.errors;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j2
public abstract class AbtractError {
    protected Logger logger = LogManager.getLogger(getClass());

    protected void logError(Throwable throwable) {
        logger.error(String.format("Error occurred in %s: %s", getClass().getName(), throwable.getMessage()), throwable);
    }

    protected void logDebug(String message) {
        logger.debug(String.format("Debug message from %s: %s", getClass().getName(), message));
    }

    protected void logInfo(String message) {
        logger.info(String.format("Info message from %s: %s", getClass().getName(), message));
    }
}
