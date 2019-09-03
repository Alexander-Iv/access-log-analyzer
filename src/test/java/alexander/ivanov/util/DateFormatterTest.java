package alexander.ivanov.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateFormatterTest {
    private static final String DATE_PATTERN = "dd/MM/yyyy:HH:mm:ss";

    @Test
    void toDateFormat() throws ParseException {
        String dateAsString = "01/01/2000:00:00:00";
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        assertEquals(format.parse(dateAsString), DateFormatter.toDateFormat(dateAsString));
    }

    @Test
    void toStringFormat() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date currentDate = new Date(System.currentTimeMillis());
        assertEquals(format.format(currentDate), DateFormatter.toStringFormat(currentDate));
    }
}