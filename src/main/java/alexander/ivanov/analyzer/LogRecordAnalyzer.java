package alexander.ivanov.analyzer;

import alexander.ivanov.model.LogRecord;
import alexander.ivanov.model.ResultRecord;
import alexander.ivanov.util.Constants;

import java.util.List;
import java.util.stream.Stream;

public interface LogRecordAnalyzer extends Analyzer<Stream<String>, Stream<LogRecord>> {
    @Override
     default void analyze(Stream<LogRecord> data) {
        analyze(data, Constants.TIME_DEFAULT.getFloatValue(), Constants.ACCESSIBILITY_DEFAULT.getFloatValue());
    }

    void analyze(Stream<LogRecord> data, Float time, Float accessibility);

    List<ResultRecord> getResult();
}
