package alexander.ivanov.analyzer;

import alexander.ivanov.model.LogRecord;

import java.util.stream.Stream;

public interface LogRecordAnalyzer extends Analyzer<Stream<String>, Stream<LogRecord>> {
    @Override
    default void analyze(Stream<LogRecord> data) {

    }

    void analyze(Stream<LogRecord> data, Integer time, Float accessibility);
}
