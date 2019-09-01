package alexander.ivanov.model;

public class LogRecordDto {
    private String date;
    private String httpStatusCode;
    private String processingTimeMs;

    public LogRecordDto() {
    }

    public LogRecordDto(String date, String httpStatusCode, String processingTimeMs) {
        this.date = date;
        this.httpStatusCode = httpStatusCode;
        this.processingTimeMs = processingTimeMs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(String processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    @Override
    public String toString() {
        return "LogRecordDto{" +
                "date='" + date + '\'' +
                ", httpStatusCode='" + httpStatusCode + '\'' +
                ", processingTimeMs='" + processingTimeMs + '\'' +
                '}';
    }
}
