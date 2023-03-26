import mockfw.API;
import mockfw.MockAnnotation;
import mockfw.MockStatic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StaticTest {

    @MockAnnotation
    private User user;

    @Test
    public void testStaticMock(){
        assert User.staticReturnString().equals("static");

        MockStatic mockStatic = API.createStaticMock(User.class);
        API.when(User.staticReturnString()).thenReturn("bb");
        assert User.staticReturnString().equals("bb");

        API.when(User.staticAnyString("aa")).thenThrow(new ArithmeticException());
        Assertions.assertThrows(ArithmeticException.class, () -> {
            User.staticAnyString("aa");
        });
    }

    @Test
    public void testThrowable(){
        MockStatic mockStatic = API.createStaticMock(UserWithArgs.class);
        API.when(UserWithArgs.getStaticThrowable("first", "second")).thenThrow(new ArithmeticException());

        Assertions.assertThrows(ArithmeticException.class, () -> {
            UserWithArgs.getStaticThrowable("first", "second");
        });
    }

    @Test
    public void testNotWorking(){
        API.when(UserWithoutArgs.staticReturnString()).thenReturn("bb");
        assert UserWithoutArgs.staticReturnString().equals("static");
    }
}
