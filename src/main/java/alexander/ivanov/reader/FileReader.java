package alexander.ivanov.reader;

import alexander.ivanov.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public BufferedReader getFileReader(String fileName) {
        logger.info("FileReader.read");
        logger.info("fileName = {}", fileName);

        Path filePath = Paths.get("./", fileName);
        logger.info("filePath = {}", filePath);

        boolean isFileExists = Files.exists(filePath);
        logger.info("isFileExists = {}", isFileExists);

        if (isFileExists) {
            try {
                return Files.newBufferedReader(filePath);
            } catch (IOException e) {
                ErrorHandler.printStackTrace(e.getStackTrace());
                throw new RuntimeException(e.getMessage());
            }

        } else {
            throw new RuntimeException("File not found!");
        }
    }
}
