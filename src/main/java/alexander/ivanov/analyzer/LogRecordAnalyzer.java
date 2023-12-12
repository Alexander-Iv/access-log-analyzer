package alexander.ivanov.analyzer;

import alexander.ivanov.model.ResultRecord;

import java.util.List;
import java.util.stream.Stream;

/**
 * Performs analyze text data stream.
 * Collects results in ordered collection
 *
 * <p>Example:
 *
 * @see alexander.ivanov.analyzer.impl.LogRecordAnalyzerImpl
 */
public interface LogRecordAnalyzer {
    void analyze(Stream<String> data);

    List<ResultRecord> getResultRecords();
}
