package alexander.ivanov.analyzer;

import alexander.ivanov.model.LogRecord;
import alexander.ivanov.model.ResultRecord;
import alexander.ivanov.util.Constants;

import java.util.List;
import java.util.stream.Stream;

public interface LogRecordAnalyzer extends Analyzer<Stream<String>, Stream<LogRecord>> {
    @Override
     default void analyze(Stream<LogRecord> data) {
        analyze(data,
                Constants.TIME_DEFAULT.getFloatValue(),
                Constants.ACCESSIBILITY_DEFAULT.getFloatValue(),
                Constants.LINES_DEFAULT.getIntValue(),
                Constants.TEST_MODE.getIntValue()
        );
    }

    void analyze(Stream<LogRecord> data, Float time, Float accessibility, Integer linesCount, Integer testMode);

    List<ResultRecord> getResult();
}
