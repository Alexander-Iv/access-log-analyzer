package alexander.ivanov;

import alexander.ivanov.analyzer.LogRecordAnalyzer;
import alexander.ivanov.analyzer.impl.LogRecordAnalyzerImpl;
import alexander.ivanov.reader.BufferedReaderCreator;
import alexander.ivanov.util.CliHelper;
import alexander.ivanov.util.Constants;
import alexander.ivanov.util.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        App.run(args);
    }

    public static class App {
        private static final Logger logger = LoggerFactory.getLogger(App.class);

        private static float time;
        private static float accessibility;
        private static int linesCount;
        private static int testMode;
        private static String fileName;

        private static LogRecordAnalyzer analyzer;
        private static BufferedReader bufferedReader;

        private static void run(String[] args) {
            init(args);

            if (CliHelper.isHelpExists()) {
                CliHelper.printHelp();
                return;
            }
            analyzeInputStream();
        }

        private static void init(String[] args) {
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

            analyzer = new LogRecordAnalyzerImpl(
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

                    if (reader.getLineNumber()%linesCount == 0 ) {
                        analyzer.analyze(lines.stream());
                        lines.clear();
                    }
                });

                if (!lines.isEmpty()) {
                    analyzer.analyze(lines.stream());
                    lines.clear();
                }
                if (testMode == 1) {
                    logger.info("all records count = {}", analyzer.getResultRecords().size());
                    logger.info("**********************");
                }
            }
        }

        public static LogRecordAnalyzer getAnalyzer() {
            return analyzer;
        }
    }
}
