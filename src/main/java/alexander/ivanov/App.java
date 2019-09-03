package alexander.ivanov;

import alexander.ivanov.analyzer.LogRecordAnalyzer;
import alexander.ivanov.analyzer.impl.LogRecordAnalyzerImpl;
import alexander.ivanov.model.LogRecord;
import alexander.ivanov.reader.FileReader;
import alexander.ivanov.util.CliHelper;
import alexander.ivanov.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static LogRecordAnalyzer analyzer = new LogRecordAnalyzerImpl();
    private static int time;
    private static float accessibility;
    private static int linesCount;
    private static String fileName;

    public static void main(String[] args) {
        logger.info("App.main");
        initOpts(args);
        runAnalyze();
    }

    private static void initOpts(String[] args) {
        logger.info("App.initOpts");
        logger.info("args = {}", Arrays.asList(args));

        CliHelper.parseArgs(args);

        time = CliHelper.getTimeValue();
        accessibility = CliHelper.getAccessibilityValue();
        linesCount = CliHelper.getLinesValue();
        fileName = CliHelper.getFileNameValue();

        logger.info("time = {}", time);
        logger.info("accessibility = {}", accessibility);
        logger.info("lines = {}", linesCount);
        logger.info("fileName = {}", fileName);
    }

    public static void runAnalyze() {
        analyzeInputStream();
        analyzer.printResult();
    }

    private static void analyzeInputStream() {
        try (LineNumberReader reader = new LineNumberReader(getReader())) {
            analyzeNonEmptyInputStream(reader);
        } catch (IOException e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static BufferedReader getReader() {
        if (!fileName.isEmpty()) {
            return new FileReader().getFileReader(fileName);
        } else {
            return new BufferedReader(new InputStreamReader(System.in));
        }
    }

    private static void analyzeNonEmptyInputStream(LineNumberReader reader) throws IOException {
        if (reader.ready()) {
            List<String> lines = new ArrayList<>(linesCount);
            while (true) {
                String tmp = null;
                while ((tmp = reader.readLine()) != null && reader.getLineNumber() < linesCount) {
                    lines.add(tmp);
                }

                if (!lines.isEmpty()) {
                    analyze(analyzer, lines.stream());
                    reader.setLineNumber(0);
                    lines.clear();
                }

                if (tmp == null) {
                    return;
                }
            }
        }
    }

    private static void analyze(LogRecordAnalyzer analyzer, Stream<String> inputStream) {
        Stream<LogRecord> logRecordsAsStream = analyzer.convert(inputStream);
        analyzer.analyze(logRecordsAsStream, time, accessibility);
    }
}
