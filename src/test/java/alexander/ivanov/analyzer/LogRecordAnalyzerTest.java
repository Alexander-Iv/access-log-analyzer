package alexander.ivanov.analyzer;

import alexander.ivanov.app.App;
import alexander.ivanov.app.impl.impl.AppImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

class LogRecordAnalyzerTest {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAnalyzerTest.class);
    private static final String[] DATA = new String[] {
            "192.168.32.181 - - [01/01/2000:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 01.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2001:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 10.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2002:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 20.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2003:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 30.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2004:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 40.00 \"-\" \"@list-item-updater\" prio:0\n",

            "192.168.32.181 - - [01/01/2005:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 50.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2004:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 50.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2003:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 50.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2002:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 50.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2001:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 50.00 \"-\" \"@list-item-updater\" prio:0\n",

            "192.168.32.181 - - [01/01/2000:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 500 2 01.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2000:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 500 2 10.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2000:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 500 2 20.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2000:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 500 2 30.00 \"-\" \"@list-item-updater\" prio:0\n",
            "192.168.32.181 - - [01/01/2000:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 500 2 40.00 \"-\" \"@list-item-updater\" prio:0\n",

            "192.168.32.181 - - [01/01/2020:00:00:00 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=b0305b29 HTTP/1.1\" 200 2 50.00 \"-\" \"@list-item-updater\" prio:0\n"
    };

    @Test
    void convert() {
        String[] args = new String[]{"-t", "0", "-a", "100.0", "-nl", "100"};
        App app = new AppImpl(args);

        byte[] bytes = Arrays.stream(DATA).map(String::getBytes).reduce((bytes1, bytes2) -> {
            byte[] newBytes = new byte[bytes1.length + bytes2.length];
            System.arraycopy(bytes1, 0, newBytes, 0, bytes1.length);
            System.arraycopy(bytes2, 0, newBytes, bytes1.length, bytes2.length);
            return newBytes;
        }).get();

        System.setIn(new ByteArrayInputStream(bytes));

        app.init();
        app.run();

        /*LogRecordAnalyzer analyzer = (LogRecordAnalyzer)app.getAnalyzer();;
        Stream<LogRecord> logRecordsAsStream = analyzer.convert(Arrays.stream(DATA));
        //logRecordsAsStream.collect(Collectors.toList()).forEach(logRecord -> logger.info("logRecord = {}", logRecord));
        analyzer.analyze(
                logRecordsAsStream,
                40,
                90.0F
        );
        analyzer.getResult().forEach(resultRecord -> {
            logger.info("resultRecord = {}", resultRecord);
        });*/

    }

    @Test
    void analyze() {

    }
}