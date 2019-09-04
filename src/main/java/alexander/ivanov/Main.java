package alexander.ivanov;

import alexander.ivanov.app.App;
import alexander.ivanov.app.impl.impl.AppImpl;

public class Main {
    public static void main(String[] args) {
        App app = new AppImpl(args);
        app.init();
        app.run();
    }
}
