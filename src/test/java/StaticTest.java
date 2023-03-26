import mockfw.API;
import mockfw.MockStatic;
import mockfw.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

public class StaticTest {

    @Test
    public void testStaticMock(){
        assert User.staticReturnString().equals("static");

        try (MockStatic mockStatic = API.createStaticMock(User.class)){
            API.when(User.staticReturnString()).thenReturn("bb");
            assert User.staticReturnString().equals("bb");

            API.when(User.staticAnyString("aa")).thenThrow(new ArithmeticException());
            Assertions.assertThrows(ArithmeticException.class, () -> User.staticAnyString("aa"));

            API.when(User.staticAnyString("bb")).thenThrow(new EmptyStackException());
            Assertions.assertThrows(EmptyStackException.class, () -> User.staticAnyString("bb"));
        }
        assert User.staticReturnString().equals("static");
    }

    @Test
    public void testThrowable(){
        assert UserWithoutArgs.staticReturnString().equals("static");
        try (MockStatic mockStatic = API.createStaticMock(UserWithoutArgs.class)){
            API.when(UserWithoutArgs.staticReturnString()).thenThrow(new ArithmeticException());
            Assertions.assertThrows(ArithmeticException.class, UserWithoutArgs::staticReturnString);
        }
        assert UserWithoutArgs.staticReturnString().equals("static");
    }

    @Test
    public void testNotWorking(){
        API.when(User.staticReturnString()).thenReturn("bb");
        assert User.staticReturnString().equals("static");
    }
}
