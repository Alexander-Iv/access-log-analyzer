package alexander.ivanov.app;

import alexander.ivanov.analyzer.LogRecordAnalyzer;

public interface App {
    void init();
    void run();
    LogRecordAnalyzer getAnalyzer();
}
