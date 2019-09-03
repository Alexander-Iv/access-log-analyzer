package alexander.ivanov.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CliHelperTest {
    private static final int TIME_DEFAULT = 50;
    private static final float ACCESSIBILITY_DEFAULT = 99.9F;

    private static final String TIME_OPT = "-t";
    private static final String ACCESSIBILITY_OPT = "-a";

    private static String[] args;

    @BeforeEach
    void init() {
        args = new String[]{TIME_OPT, String.valueOf(TIME_DEFAULT), ACCESSIBILITY_OPT, String.valueOf(ACCESSIBILITY_DEFAULT)};
        System.out.println("Arrays.asList(args) = " + Arrays.asList(args));
        CliHelper.parseArgs(args);
    }

    @Test
    void parseArgs() {
        assertNotNull(CliHelper.parseArgs(args));
        assertDoesNotThrow(() -> CliHelper.parseArgs(args));
    }

    @Test
    void getTimeValue() {
        assertEquals(CliHelper.getTimeValue(), TIME_DEFAULT);
    }

    @Test
    void getAccessibilityValue() {
        assertEquals(CliHelper.getAccessibilityValue(), ACCESSIBILITY_DEFAULT);
    }
}