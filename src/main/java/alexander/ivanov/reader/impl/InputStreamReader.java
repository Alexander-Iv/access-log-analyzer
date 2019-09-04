package alexander.ivanov.reader.impl;

import alexander.ivanov.reader.Reader;

import java.io.BufferedReader;

public class InputStreamReader implements Reader<BufferedReader> {
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new java.io.InputStreamReader(System.in));
    }
}
