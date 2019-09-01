package alexander.ivanov;

import alexander.ivanov.analyzer.LogRecordAnalyzer;
import alexander.ivanov.analyzer.impl.LogRecordAnalyzerImpl;
import alexander.ivanov.model.LogRecord;
import alexander.ivanov.util.CliHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final short BATCH_SIZE = 1000;
    private static int time;
    private static float accessibility;

    public static void main(String[] args) throws IOException {
        logger.info("App.main");
        logger.info("args = {}", Arrays.asList(args));

        CliHelper.parseArgs(args);
        time = CliHelper.getTimeValue();
        accessibility = CliHelper.getAccessibilityValue();
        logger.info("time = {}", time);
        logger.info("accessibility = {}", accessibility);

        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(System.in))) {
            //logger.info("br.lines() = {}", reader.lines());
            LogRecordAnalyzer analyzer = new LogRecordAnalyzerImpl();
            List<String> lines = new ArrayList<>(BATCH_SIZE);
            while (true) {
                String tmp = null;
                while ((tmp = reader.readLine()) != null && reader.getLineNumber() < BATCH_SIZE) {
                    //logger.info("tmp = {}, ln = {}", tmp, reader.getLineNumber());
                    lines.add(tmp);
                }
                /*lines.forEach(s -> {
                    logger.info("line = {}", s);
                });*/

                if (!lines.isEmpty()) {
                    //logger.info("before lines.size() = {}, lines = {}", lines.size(), lines);
                    Stream<LogRecord> logRecordsAsStream = analyzer.convert(lines.stream());
                    analyzer.analyze(logRecordsAsStream, time, accessibility);
                    reader.setLineNumber(0);
                    lines.clear();
                    //logger.info("after clear lines.size() = {}, lines = {}", lines.size(), lines);
                }
                //logger.info("after all tmp = {}", tmp);
                if (tmp == null) {
                    return;
                }
            }
        }

        //Stream<String> readedLinesAsStream = new FileReader().read("access.log");
        /*LogRecordAnalyzer analyzer = new LogRecordAnalyzerImpl();
        Stream<LogRecord> logRecordsAsStream = analyzer.convert(readedLinesAsStream);
        analyzer.analyze(logRecordsAsStream);*/
    }
}
