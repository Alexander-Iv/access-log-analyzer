package ru.aaivn.loganalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aaivn.loganalyzer.util.DateFormatter;
import ru.aaivn.loganalyzer.util.FloatFormatter;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultRecord {
    public Date startDate;
    public Date endDate;
    public float accessibility;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultRecord that = (ResultRecord) o;
        return Float.compare(FloatFormatter.transformFormat(that.accessibility), FloatFormatter.transformFormat(accessibility)) == 0 &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, accessibility);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",
                DateFormatter.toStringFormat(startDate),
                DateFormatter.toStringFormat(endDate),
                FloatFormatter.toStringForamt(accessibility)
        );
    }
}
