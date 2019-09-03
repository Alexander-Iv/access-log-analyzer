package alexander.ivanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {

    @Test
    void runAppWithoutData() {
        assertDoesNotThrow(() -> App.main(new String[]{"-t", "45", "-a", "99.9"}));
    }

    @Test
    void runAppWithDataFromFile() {
        assertDoesNotThrow(() -> App.main(new String[]{"-t", "40", "-a", "90.0", "-f", "access.log"}));
    }

    @Test
    void runAnalyze() {
    }
}