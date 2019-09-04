package alexander.ivanov.reader;

import java.io.BufferedReader;

public interface ReaderCreator<R extends java.io.Reader> extends Reader<R> {
    R create();
}
