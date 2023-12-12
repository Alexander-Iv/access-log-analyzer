package ru.aaivn.loganalyzer;

import ru.aaivn.loganalyzer.reader.BufferedReaderCreator;
import ru.aaivn.loganalyzer.util.CliHelper;
import ru.aaivn.loganalyzer.util.Constants;
import ru.aaivn.loganalyzer.util.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Application {
    private static float time;
    private static float accessibility;
    private static int linesCount;
    private static int testMode;
    private static String fileName;
    private static LogAnalyzer analyzer;
    private static BufferedReader bufferedReader;

    public static void main(String[] args) {
        init(args);

        if (CliHelper.isHelpExists()) {
            CliHelper.printHelp();
            return;
        }
        analyzeInputStream();
    }

    public static LogAnalyzer getLogAnalyzer() {
         return analyzer;
    }

    private static void init(String[] args) {
        CliHelper.parseArgs(args);

        time = CliHelper.getTimeValue();
        accessibility = CliHelper.getAccessibilityValue();
        linesCount = CliHelper.getLinesValue();
        testMode = CliHelper.getTestModeValue();
        fileName = CliHelper.getFileNameValue();

        if (testMode == 1) {
            log.info("args = {}", Arrays.asList(args));
            log.info("time = {}", time);
            log.info("accessibility = {}", accessibility);
            log.info("linesCount = {}", linesCount);
            log.info("fileName = {}", fileName);
        }

        analyzer = new LogAnalyzer(
                Constants.MIN_HTTP_STATUS_CODE.getIntValue(),
                Constants.MAX_HTTP_STATUS_CODE.getIntValue(),
                time, accessibility, linesCount, testMode
        );
        bufferedReader = new BufferedReaderCreator(fileName).getReader();
    }

    private static void analyzeInputStream() {
        try (LineNumberReader reader = new LineNumberReader(bufferedReader)) {
            analyzeNonEmptyInputStream(reader);
        } catch (IOException e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void analyzeNonEmptyInputStream(LineNumberReader reader) throws IOException {
        if (reader.ready()) {
            List<String> lines = new ArrayList<>(linesCount);

            reader.lines().forEach(s -> {
                lines.add(s);

                if (reader.getLineNumber() % linesCount == 0) {
                    analyzer.analyze(lines.stream());
                    lines.clear();
                }
            });

            if (!lines.isEmpty()) {
                analyzer.analyze(lines.stream());
                lines.clear();
            }
            if (testMode == 1) {
                log.info("all records count = {}", analyzer.getResultRecords().size());
                log.info("**********************");
            }
        }
    }
}
