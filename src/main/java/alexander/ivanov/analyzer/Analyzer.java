package alexander.ivanov.analyzer;

public interface Analyzer<T, R> {
    R convert(T data);
    void analyze(R data);
    void printResult();
}
