package mockfw;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MocksSettings {
    private static final HashMap<Class, Mock> globalState = new HashMap<>();

    public static boolean exists(Class c) {
        return globalState.containsKey(c);
    }

    public static void addNewMock(Class c, Mock mock) {
        globalState.put(c, mock);
    }

    public static Mock getMock(Class c) {
        return globalState.get(c);
    }

    public static HashMap<Class, Mock> getGlobalState() {
        return globalState;
    }
}
