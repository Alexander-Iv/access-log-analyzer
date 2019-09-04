package alexander.ivanov;

import alexander.ivanov.analyzer.Analyzer;

public interface App {
    void init();
    void run();
    Analyzer<?,?> getAnalyzer();
}
