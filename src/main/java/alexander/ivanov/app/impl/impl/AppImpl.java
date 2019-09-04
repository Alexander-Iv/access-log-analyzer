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
import java.util.List;
import java.util.stream.Stream;

public class AppImpl implements App {
    private static final Logger logger = LoggerFactory.getLogger(AppImpl.class);

    private String[] args;

    private int time;
    private float accessibility;
    private int linesCount;
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
        fileName = CliHelper.getFileNameValue();

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
                    analyzer.printResult();
                    return;
                }
            }
        }
    }

    private void analyze(LogRecordAnalyzer analyzer, Stream<String> inputStream) {
        Stream<LogRecord> logRecordsAsStream = analyzer.convert(inputStream);
        analyzer.analyze(logRecordsAsStream, time, accessibility);
    }
}
