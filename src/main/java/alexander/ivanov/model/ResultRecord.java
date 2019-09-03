package alexander.ivanov.model;

import alexander.ivanov.util.DateFormatter;

import java.util.Date;

public class ResultRecord {
    public Date startDate;
    public Date endDate;
    public float accessibility;

    public ResultRecord() {
    }

    public ResultRecord(Date startDate, Date endDate, float accessibility) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.accessibility = accessibility;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(float accessibility) {
        this.accessibility = accessibility;
    }

    @Override
    public String toString() {
        return "ResultRecord{" +
                "startDate=" + DateFormatter.toStringFormat(startDate) +
                ", endDate=" + DateFormatter.toStringFormat(endDate) +
                ", accessibility=" + String.format("%.02f", accessibility) +
                '}';
    }
}
