package alexander.ivanov.util;

public enum Constants {
    TIME_DEFAULT(45),
    LINES_DEFAULT(100),
    ACCESSIBILITY_DEFAULT(90.0F);

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
