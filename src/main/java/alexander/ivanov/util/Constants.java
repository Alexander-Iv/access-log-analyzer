package alexander.ivanov.util;

public enum Constants {
    TIME_DEFAULT(45.0F),
    ACCESSIBILITY_DEFAULT(90.0F),
    LINES_DEFAULT(100);

    private Number value;

    Constants(Number value) {
        this.value = value;
    }

    public int getIntValue() {
        return value.intValue();
    }

    public float getFloatValue() {
        return value.floatValue();
    }
}
