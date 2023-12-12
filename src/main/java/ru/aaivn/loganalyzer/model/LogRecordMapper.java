package ru.aaivn.loganalyzer.model;

import ru.aaivn.loganalyzer.util.DateFormatter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.Date;

public enum LogRecordMapper {
    INSTANCE;

    private final TypeMap<LogRecordDto, LogRecord> mapper;

    LogRecordMapper() {
        this(new ModelMapper());
    }

    LogRecordMapper(ModelMapper modelMapper) {
        mapper = modelMapper.createTypeMap(LogRecordDto.class, LogRecord.class)
                .addMappings(mapping -> mapping.using(new LogRecordDateConverter())
                        .map(LogRecordDto::getDate, LogRecord::setDate));
    }

    public LogRecord fromDto(LogRecordDto logRecordDto) {
        return mapper.map(logRecordDto);
    }

    public static LogRecord map(LogRecordDto logRecordDto) {
        return INSTANCE.fromDto(logRecordDto);
    }

    private static class LogRecordDateConverter extends AbstractConverter<String, Date> {
        @Override
        protected Date convert(String source) {
            return DateFormatter.toDateFormat(source);
        }
    }
}
