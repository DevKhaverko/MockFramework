import mockfw.API;
import mockfw.MockAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnnotationTest {
    @MockAnnotation
    UserWithArgs userWithArgs;

    @Test
    public void testAnnotationWork(){
        API.initMocks(this);
        API.when(userWithArgs.getName()).thenReturn("HEY");
        assert userWithArgs.getName().equals("HEY");
    }

    @Test
    public void checkCreatingMock() {
        API.initMocks(this);
        API.when(userWithArgs.getName()).thenReturn("HEY");
        assert userWithArgs.getName().equals("HEY");
    }

    @Test
    public void checkWhenWithArgsAndThenReturn() {
        API.initMocks(this);
        API.when(userWithArgs.getSomethingInteresting("first", "second")).thenReturn("DONE");
        assert userWithArgs.getSomethingInteresting("first", "second").equals("DONE");
    }

    @Test
    public void checkWhenWithArgsAndThenThrow() {
        API.initMocks(this);
        API.when(userWithArgs.getThrowable("first", "second")).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(IllegalArgumentException.class, () -> userWithArgs.getThrowable("first", "second"));
    }
}
