package alexander.ivanov.analyzer;

import alexander.ivanov.model.LogRecord;
import alexander.ivanov.model.ResultRecord;

import java.util.List;
import java.util.stream.Stream;

public interface LogRecordAnalyzer extends Analyzer<Stream<String>, Stream<LogRecord>> {
    @Override
    default void analyze(Stream<LogRecord> data) {

    }

    void analyze(Stream<LogRecord> data, Integer time, Float accessibility);

    List<ResultRecord> getResult();
}
