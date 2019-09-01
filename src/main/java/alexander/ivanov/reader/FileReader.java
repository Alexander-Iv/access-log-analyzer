package alexander.ivanov.reader;

import alexander.ivanov.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public Stream<String> read(String fileName) {
        logger.info("FileReader.read");
        logger.info("fileName = {}", fileName);

        Path filePath = Paths.get("./", fileName);
        logger.info("filePath = {}", filePath);

        boolean isFileExists = Files.exists(filePath);
        logger.info("isFileExists = {}", isFileExists);

        if (isFileExists) {
            long fileSize = 0;
            try {
                fileSize = Files.size(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("fileSize = {}", fileSize);

            /*ByteBuffer buffer = ByteBuffer.allocate(536870912);
            logger.info("new String(buffer.array()) = {}", new String(buffer.array()));
            Stream.of(buffer.get()).map(String::valueOf).forEach(s -> logger.info("s = {}", s));*/

            return getFileContentAsStream(filePath);

        } else {
            throw new RuntimeException("File not found!");
        }
    }

    private Stream<String> getFileContentAsStream(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
    }
}
