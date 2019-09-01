package alexander.ivanov.model;

import alexander.ivanov.util.DateFormatter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.Date;

public class LogRecordMapper {
    private static final TypeMap<LogRecordDto, LogRecord> mapper;

    static {
        mapper = new ModelMapper()
                .createTypeMap(LogRecordDto.class, LogRecord.class)
                .addMappings(mapping -> mapping.using(new LogRecordDateConverter()).map(LogRecordDto::getDate, LogRecord::setDate))
        ;
    }

    public static LogRecord map(LogRecordDto logRecordDto) {
        LogRecord logRecord = mapper.map(logRecordDto);
        return logRecord;
    }

    private static class LogRecordDateConverter extends AbstractConverter<String, Date> {
        @Override
        protected Date convert(String source) {
            return DateFormatter.toDate(source);
        }
    }
}
