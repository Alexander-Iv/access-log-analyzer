package alexander.ivanov.model;

import alexander.ivanov.util.DateFormatter;

import java.util.Date;

public class LogRecord {
    private Date date;
    private short httpStatusCode;
    private float processingTimeMs;

    public LogRecord() {
    }

    public LogRecord(Date date, short httpStatusCode, float processingTimeMs) {
        this.date = date;
        this.httpStatusCode = httpStatusCode;
        this.processingTimeMs = processingTimeMs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public short getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(short httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public float getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(float processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogRecord)) return false;

        LogRecord logRecord = (LogRecord) o;

        if (httpStatusCode != logRecord.httpStatusCode) return false;
        if (Float.compare(processingTimeMs, logRecord.processingTimeMs) != 0) return false;
        return date.equals(logRecord.date);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + (int) httpStatusCode;
        result = 31 * result + (processingTimeMs != 0.0f ? Float.floatToIntBits(processingTimeMs) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "date=" + DateFormatter.toStringFormat(date) +
                ", httpStatusCode=" + httpStatusCode +
                ", processingTimeMs=" + processingTimeMs +
                '}';
    }
}
