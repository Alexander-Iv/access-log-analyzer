package alexander.ivanov.analyzer.impl;

import alexander.ivanov.analyzer.LogRecordAnalyzer;
import alexander.ivanov.model.LogRecord;
import alexander.ivanov.model.LogRecordDto;
import alexander.ivanov.model.LogRecordMapper;
import alexander.ivanov.model.ResultRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LogRecordAnalyzerImpl implements LogRecordAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAnalyzerImpl.class);
    private List<ResultRecord> resultRecords = new ArrayList<>();
    private List<ResultRecord> fullResultRecords = new ArrayList<>();

    @Override
    public Stream<LogRecord> convert(Stream<String> data) {
        return data
                .map(s -> s.split(" "))
                //.peek(strings -> logger.info("Arrays.asList(strings) = {}", Arrays.asList(strings)))
                .map(strings -> LogRecordMapper.map(new LogRecordDto(strings[3].substring(1), strings[8], strings[10])));
    }

    @Override
    public void analyze(Stream<LogRecord> data, Float time, Float accessibility) {
        resultRecords = new ArrayList<>();
        Map<Boolean, List<LogRecord>> result = data.collect(Collectors.partitioningBy(logRecord ->
            (logRecord.getHttpStatusCode() >= 500 && logRecord.getHttpStatusCode() < 600) || logRecord.getProcessingTimeMs() > time)
        );

        result.forEach((aBoolean, logRecords) -> {
            if (aBoolean && !logRecords.isEmpty()) {

                long from = getMinDate(getTimeStream(logRecords));
                long to = getMaxDate(getTimeStream(logRecords));

                float successCount = result.get(false).size();
                float failureCount = result.get(true).size();
                float quantity = successCount + failureCount;

                float currAccessibility = calcAccessibility(successCount, quantity);
                logger.info("currAccessibility = {}", currAccessibility);
                if (currAccessibility <= accessibility) {
                    resultRecords.add(new ResultRecord(new Date(from), new Date(to), currAccessibility));
                    fullResultRecords.addAll(resultRecords);
                    printLogRecords(logRecords);
                }
            }
        });
    }

    @Override
    public void printResult() {
        getAscSortedResult(resultRecords).forEach(System.out::println);
    }

    @Override
    public List<ResultRecord> getResult() {
        return getAscSortedResult(fullResultRecords);
    }

    private List<ResultRecord> getAscSortedResult(List<ResultRecord> resultRecords) {
        resultRecords.sort(Comparator.comparing(o -> o.startDate));
        return resultRecords;
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
