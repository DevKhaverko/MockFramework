import mockfw.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DynamicMethodsTest {
    @Test
    public void checkCreatingMockFromClassWithConstructorWithoutArgs() {
        UserWithoutArgs userWithoutArgs = API.mock(UserWithoutArgs.class);
        API.when(userWithoutArgs.getName()).thenReturn("WOW");
        assert userWithoutArgs.getName().equals("WOW");
    }
    @Test
    public void checkCreatingMockFromClassWithConstructorWithArgs() {
        UserWithArgs userWithArgs = API.mock(UserWithArgs.class);
        API.when(userWithArgs.getName()).thenReturn("HEY");
        assert userWithArgs.getName().equals("HEY");
    }

    @Test
    public void checkWhenWithArgsAndThenReturn() {
        UserWithArgs userWithArgs = API.mock(UserWithArgs.class);
        API.when(userWithArgs.getSomethingInteresting("first", "second")).thenReturn("DONE");
        assert userWithArgs.getSomethingInteresting("first", "second").equals("DONE");
    }

    @Test
    public void checkWhenWithArgsAndThenThrow() {
        UserWithArgs userWithArgs = API.mock(UserWithArgs.class);
        API.when(userWithArgs.getThrowable("first", "second")).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userWithArgs.getThrowable("first", "second");
        });
    }

    @Test
    public void checkWithInterfaces() {
        TestInterface testInterface = API.mock(Implementor.class);
        API.when(testInterface.get(3)).thenReturn(4);
        API.when(testInterface.get(2)).thenThrow(new IllegalArgumentException());
        API.when(testInterface.getBool()).thenReturn(false);

        assert testInterface.get(3).equals(4);
        assert testInterface.getBool().equals(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           testInterface.get(2);
        });
    }
}
