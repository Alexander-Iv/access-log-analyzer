package ru.aaivn.loganalyzer;

import ru.aaivn.loganalyzer.model.LogRecord;
import ru.aaivn.loganalyzer.model.LogRecordDto;
import ru.aaivn.loganalyzer.model.LogRecordMapper;
import ru.aaivn.loganalyzer.model.ResultRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Анализатор логов
 */
@Slf4j
@RequiredArgsConstructor
public class LogAnalyzer {
    private List<ResultRecord> resultRecords;
    private List<ResultRecord> fullResultRecords = new ArrayList<>();

    private final int minHttpStatusCode;
    private final int maxHttpStatusCode;

    private final Float time;
    private final Float accessibility;
    private final Integer linesCount;
    private final Integer testMode;

    /**
     * Анализ данных логов
     * @param logData данные логов
     */
    public void analyze(Stream<String> logData) {
        resultRecords = new ArrayList<>(linesCount);

        // маппинг в pojo
        Stream<LogRecord> logRecords = logData
                .map(s -> s.split(" "))
                .map(rawLog -> {
                    var logDate = rawLog[3].substring(1);
                    var httpStatusCode = rawLog[8];
                    var processingTime = rawLog[10];
                    var logRecordDto = new LogRecordDto(logDate, httpStatusCode, processingTime);
                    return LogRecordMapper.map(logRecordDto);
                });

        // разбиение по диапазону статусов и порогу затраченного времени
        Map<Boolean, List<LogRecord>> partitionedLogRecords = logRecords.collect(Collectors.partitioningBy(logRecord ->
                (logRecord.getHttpStatusCode() >= minHttpStatusCode
                        && logRecord.getHttpStatusCode() < maxHttpStatusCode)
                        || logRecord.getProcessingTimeMs() > time));

        partitionedLogRecords.forEach((isErrorRecordExists, records) -> {
            if (isErrorRecordExists && !records.isEmpty()) {

                float successCount = partitionedLogRecords.get(false).size();
                float failureCount = partitionedLogRecords.get(true).size();
                float quantity = successCount + failureCount;

                float calculatedAccessibility = successCount / quantity * 100;
                if (calculatedAccessibility <= accessibility) {
                    var startDate = getTimeStream(records)
                            .min(Long::compareTo)
                            .map(Date::new)
                            .orElseThrow();
                    var endDate = getTimeStream(records)
                            .max(Long::compareTo)
                            .map(Date::new)
                            .orElseThrow();
                    resultRecords.add(new ResultRecord(startDate, endDate, calculatedAccessibility));

                    if (testMode == 1) {
                        fullResultRecords.addAll(resultRecords);

                        var outputRecords = records.stream()
                                .map(LogRecord::toString)
                                .collect(Collectors.joining("\n"));
                        if (!outputRecords.isEmpty()) {
                            log.info("LogRecords:\n{}", outputRecords);
                        }
                    }
                }
            }
        });

        // вывод результата
        getAscSortedResult(resultRecords)
                .forEach(resultRecord -> log.info("{}", resultRecord));
    }

    /**
     * Получить результат анализа логов
     */
    public List<ResultRecord> getResultRecords() {
        return getAscSortedResult(fullResultRecords);
    }

    private Stream<Long> getTimeStream(List<LogRecord> records) {
        return records.stream()
                .mapToLong(logRecord -> logRecord.getDate().getTime())
                .boxed();
    }

    private List<ResultRecord> getAscSortedResult(List<ResultRecord> resultRecords) {
        resultRecords.sort(Comparator.comparing(o -> o.startDate));
        return resultRecords;
    }
}
