package ru.aaivn.loganalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecord {
    private Date date;
    private short httpStatusCode;
    private float processingTimeMs;
}
