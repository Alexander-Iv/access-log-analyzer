package alexander.ivanov.model;

import alexander.ivanov.util.DateFormatter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.Date;

public class LogRecordMapper {
    private final TypeMap<LogRecordDto, LogRecord> mapper;

    public LogRecordMapper() {
        this(new ModelMapper());
    }

    public LogRecordMapper(ModelMapper modelMapper) {
        this.mapper = modelMapper.createTypeMap(LogRecordDto.class, LogRecord.class)
                .addMappings(mapping -> mapping.using(new LogRecordDateConverter())
                        .map(LogRecordDto::getDate, LogRecord::setDate));
    }

    public LogRecord fromDto(LogRecordDto logRecordDto) {
        return mapper.map(logRecordDto);
    }

    public static LogRecord map(LogRecordDto logRecordDto) {
        return new LogRecordMapper()
                .fromDto(logRecordDto);
    }

    private static class LogRecordDateConverter extends AbstractConverter<String, Date> {
        @Override
        protected Date convert(String source) {
            return DateFormatter.toDateFormat(source);
        }
    }
}
