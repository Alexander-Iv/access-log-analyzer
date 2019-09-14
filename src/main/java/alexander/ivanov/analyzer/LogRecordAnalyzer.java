package alexander.ivanov.analyzer;

import alexander.ivanov.model.LogRecord;
import alexander.ivanov.model.ResultRecord;

import java.util.List;
import java.util.stream.Stream;

public interface LogRecordAnalyzer {
    Stream<LogRecord> convert(Stream<String> data);
    void analyze(Stream<LogRecord> data, Float time, Float accessibility, Integer linesCount, Integer testMode);
    void printResult();
    List<ResultRecord> getResult();
}
