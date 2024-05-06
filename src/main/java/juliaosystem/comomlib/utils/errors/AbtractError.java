package juliaosystem.comomlib.utils.errors;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j2
public abstract class AbtractError extends  Exception  {
    protected Logger logger = LogManager.getLogger(getClass());
    private  final Logger logg = LogManager.getLogger(getClass());


    public void logError(Throwable throwable) {
        logger.error("Error occurred in %s: %s", getClass().getName() + getLocalizedMessage() ,throwable);
    }

    public void logDebug(String message) {
        logger.debug("Debug message from %s: %s", getClass().getName(), message);
    }

    public void logInfo(String message) {
        logger.info("Info message from %s: %s", getClass().getName(), message);
    }

    public AbtractError(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
