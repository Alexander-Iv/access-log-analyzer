package alexander.ivanov.reader.impl;

import alexander.ivanov.reader.Reader;
import alexander.ivanov.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader implements Reader<BufferedReader> {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);
    private static final String FILE_NOT_FOUND = "File not found!";
    private String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public BufferedReader getReader() {
        return getFileReader(fileName);
    }

    private BufferedReader getFileReader(String fileName) {
        Path filePath = getFilePathFromCurrentDir(fileName);
        boolean isFileExists = isFileExists(filePath);
        if (isFileExists) {
            try {
                return Files.newBufferedReader(filePath);
            } catch (IOException e) {
                ErrorHandler.printStackTrace(e.getStackTrace());
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
    }

    private Path getFilePathFromCurrentDir(String fileName) {
        return Paths.get(/*"./", */fileName);
    }

    private boolean isFileExists(Path path) {
        return Files.exists(path);
    }
}
