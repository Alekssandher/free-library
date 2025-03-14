package alekssandher.free_library.config;

import alekssandher.free_library.utils.Snowflake;

public class SnowFlakeSing {
    private static volatile Snowflake instance;

    private SnowFlakeSing() {}

    public static Snowflake getInstance() {
        if (instance == null) {
            synchronized (SnowFlakeSing.class) {
                if (instance == null) {
                    instance = new Snowflake();
                }
            }
        }
        return instance;
    }
}
