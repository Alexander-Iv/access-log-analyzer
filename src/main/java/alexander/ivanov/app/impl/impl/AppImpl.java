package alexander.ivanov.app.impl.impl;

import alexander.ivanov.app.App;
import alexander.ivanov.analyzer.Analyzer;
import alexander.ivanov.analyzer.LogRecordAnalyzer;
import alexander.ivanov.analyzer.impl.LogRecordAnalyzerImpl;
import alexander.ivanov.model.LogRecord;
import alexander.ivanov.reader.impl.BufferedReaderCreatorImpl;
import alexander.ivanov.util.CliHelper;
import alexander.ivanov.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppImpl implements App {
    private static final Logger logger = LoggerFactory.getLogger(AppImpl.class);

    private String[] args;

    private float time;
    private float accessibility;
    private int linesCount;
    private int testMode;
    private String fileName;

    private LogRecordAnalyzer analyzer;
    private BufferedReader bufferedReader;

    public AppImpl() {
        this(new String[]{});
    }

    public AppImpl(String[] args) {
        this.args = args;
    }

    @Override
    public void init() {
        CliHelper.parseArgs(args);

        time = CliHelper.getTimeValue();
        accessibility = CliHelper.getAccessibilityValue();
        linesCount = CliHelper.getLinesValue();
        testMode = CliHelper.getTestModeValue();
        fileName = CliHelper.getFileNameValue();

        if (testMode == 1) {
            logger.info("args = {}", Arrays.asList(args));
            logger.info("time = {}", time);
            logger.info("accessibility = {}", accessibility);
            logger.info("linesCount = {}", linesCount);
            logger.info("fileName = {}", fileName);
        }

        analyzer = new LogRecordAnalyzerImpl();
        bufferedReader = new BufferedReaderCreatorImpl(fileName).getReader();
    }

    @Override
    public void run() {
        if (CliHelper.isHelpExists()) {
            CliHelper.printHelp();
            return;
        }
        analyzeInputStream();
    }

    @Override
    public Analyzer<?, ?> getAnalyzer() {
        return analyzer;
    }

    private void analyzeInputStream() {
        try (LineNumberReader reader = new LineNumberReader(bufferedReader)) {
            analyzeNonEmptyInputStream(reader);
        } catch (IOException e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void analyzeNonEmptyInputStream(LineNumberReader reader) throws IOException {
        if (reader.ready()) {
            List<String> lines = new ArrayList<>(linesCount);

            reader.lines().forEach(s -> {
                lines.add(s);

                if (reader.getLineNumber()%linesCount == 0 ) {
                    analyze(analyzer, lines.stream());
                    lines.clear();
                }
            });

            if (!lines.isEmpty()) {
                analyze(analyzer, lines.stream());
                lines.clear();
            }
            if (testMode == 1) {
                logger.info("all records count = {}", analyzer.getResult().size());
                logger.info("**********************");
            }
        }
    }

    private void analyze(LogRecordAnalyzer analyzer, Stream<String> inputStream) {
        Stream<LogRecord> logRecordsAsStream = analyzer.convert(inputStream);
        analyzer.analyze(logRecordsAsStream, time, accessibility, linesCount, testMode);
        analyzer.printResult();
    }
}
