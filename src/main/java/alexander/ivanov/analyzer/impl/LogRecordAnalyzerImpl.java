package alexander.ivanov.analyzer.impl;

import alexander.ivanov.analyzer.LogRecordAnalyzer;
import alexander.ivanov.model.LogRecord;
import alexander.ivanov.model.LogRecordDto;
import alexander.ivanov.model.LogRecordMapper;
import alexander.ivanov.util.DateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LogRecordAnalyzerImpl implements LogRecordAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAnalyzerImpl.class);

    @Override
    public Stream<LogRecord> convert(Stream<String> data) {
        return data
                .map(s -> s.split(" "))
                //.peek(strings -> logger.info("Arrays.asList(strings) = {}", Arrays.asList(strings)))
                .map(strings -> LogRecordMapper.map(new LogRecordDto(strings[3].substring(1), strings[8], strings[10])))
                /*.convert(logRecord -> (logRecord.getHttpStatusCode() >= 500 && logRecord.getHttpStatusCode() < 600)
                        || logRecord.getProcessingTimeMs() > 45)*/;
    }

    @Override
    public void analyze(Stream<LogRecord> data, Integer time, Float accessibility) {
        Map<Boolean, List<LogRecord>> result = data.collect(Collectors.partitioningBy(logRecord ->
            (logRecord.getHttpStatusCode() >= 500 && logRecord.getHttpStatusCode() < 600) || logRecord.getProcessingTimeMs() >= time)
        );

        result.forEach((aBoolean, logRecords) -> {
            if (aBoolean) {
                //printLogRecords(logRecords);
                long from = getMinDate(getTimeStream(logRecords));
                long to = getMaxDate(getTimeStream(logRecords));

                float successCount = result.get(false).size();
                float failureCount = result.get(true).size();
                float quantity = successCount + failureCount;

                float currAccessibility = calcAccessibility(successCount, quantity);
                if (accessibility < currAccessibility) {
                    logger.info("from = {}, to = {}, accessibility = {}"
                            , DateFormatter.toString(new Date(from))
                            , DateFormatter.toString(new Date(to))
                            , String.format("%.02f", currAccessibility));
                }
            }
        });
    }

    private void printLogRecords(List<LogRecord> logRecords) {
        StringBuilder tmp = new StringBuilder();
        logRecords.forEach(logRecord -> {
            tmp.append(logRecord).append("\n");
        });
        if (!logRecords.isEmpty()) {
            logger.info("LogRecords:\n{}", tmp);
        }
    }

    private LongStream getTimeStream(List<LogRecord> records) {
        return records.stream().mapToLong(logRecord -> logRecord.getDate().getTime());
    }

    private long getMinDate(LongStream logRecordsDates) {
        return logRecordsDates.min().getAsLong();
    }

    private long getMaxDate(LongStream logRecordsDates) {
        return logRecordsDates.max().getAsLong();
    }

    private float calcAccessibility(float current, float quantity) {
        return current/quantity*100;
    }
}
