package ru.aaivn.loganalyzer.reader;

import ru.aaivn.loganalyzer.util.ErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBufferedReader implements Reader<BufferedReader> {
    private final String fileName;

    public FileBufferedReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public BufferedReader getReader() {
        return getFileReader(fileName);
    }

    private BufferedReader getFileReader(String fileName) {
        var filePath = Path.of(fileName);
        var file = filePath.toFile();
        if (file.exists()) {
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
