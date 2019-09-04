package alexander.ivanov.reader.impl;

import alexander.ivanov.reader.Reader;

import java.io.BufferedReader;

public class BufferedReaderCreatorImpl implements Reader<BufferedReader> {
    private String fileName;
    private BufferedReader reader;

    public BufferedReaderCreatorImpl() {
        this("");
    }

    public BufferedReaderCreatorImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public BufferedReader getReader() {
        return getBufferedReader();
    }

    private BufferedReader getBufferedReader() {
        if (reader != null) {
            return reader;
        }

        if (!fileName.isEmpty()) {
            reader = new FileReader(fileName).getReader();
        } else {
            reader = new InputStreamReader().getReader();
        }

        return reader;
    }
}
