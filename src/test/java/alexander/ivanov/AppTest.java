package alexander.ivanov;

import alexander.ivanov.app.App;
import alexander.ivanov.app.impl.impl.AppImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {
    @Test
    void runAppWithoutData() {
        String[] args = new String[]{"-t", "40", "-a", "90.0"};

        App app = new AppImpl(args);
        app.init();

        assertDoesNotThrow(app::run);
    }

    @Test
    void runAppWithDataFromFile() {
        String[] args = new String[]{"-t", "40", "-a", "90.0", "-f", "access.log"};

        App app = new AppImpl(args);
        app.init();

        assertDoesNotThrow(app::run);
    }
}