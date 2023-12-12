package ru.aaivn.loganalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecordDto {
    private String date;
    private String httpStatusCode;
    private String processingTimeMs;
}
