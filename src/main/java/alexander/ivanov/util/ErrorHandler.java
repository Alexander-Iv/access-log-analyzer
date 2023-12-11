package alexander.ivanov.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public static void printStackTrace(StackTraceElement[] elements) {
        var messages = Arrays.stream(elements)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

        logger.error("ERRORS:\n{}", messages);
    }
}
