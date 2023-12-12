package ru.aaivn.loganalyzer.model;

import ru.aaivn.loganalyzer.util.DateFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.aaivn.loganalyzer.model.LogRecord;
import ru.aaivn.loganalyzer.model.LogRecordDto;
import ru.aaivn.loganalyzer.model.LogRecordMapper;

class LogRecordMapperTest {
    @Test
    void toLogRecord() {
        var dto = new LogRecordDto();
        dto.setDate("14/06/2017:16:47:02 +1000");
        dto.setHttpStatusCode("200");
        dto.setProcessingTimeMs("44.510983");

        var actual = LogRecordMapper.map(dto);

        var expected = new LogRecord();
        expected.setDate(DateFormatter.toDateFormat("14/06/2017:16:47:02 +1000"));
        expected.setHttpStatusCode((short) 200);
        expected.setProcessingTimeMs(44.510983f);

        Assertions.assertEquals(expected, actual);
    }
}