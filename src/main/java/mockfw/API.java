package mockfw;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class API {
    public static <T> T mock(Class<T> classToMock) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(classToMock);
        enhancer.setCallback(((MethodInterceptor) (obj, method, args, proxy) -> {
            Mock currentMock = MocksSettings.getMock(classToMock);
            if (currentMock.getState().equals(MockState.CREATED)) {
                currentMock.setState(MockState.IN_PROGRESS);
                currentMock.addNewVariant(new Variant(
                        method.getName(),
                        Arrays.asList(args),
                        null,
                        true,
                        null,
                        VariantState.CREATED
                ));
                return null;
            }
            if (currentMock.getState().equals(MockState.END)) {
                for (Variant variant : currentMock.getVariants()) {
                    if (variant.getMethod().equals(method.getName())
                            && variant.getArgs().equals(Arrays.asList(args))
                            && variant.getState().equals(VariantState.READY)) {
                        if (variant.isReturn())
                            return variant.getReturnValues();
                        else {
                            throw variant.getThrowables();
                        }
                    }
                }
            }
            if (currentMock.getVariants().size() != 0) {
                currentMock.setState(MockState.IN_PROGRESS);
                currentMock.addNewVariant(new Variant(
                        method.getName(),
                        Arrays.asList(args),
                        null,
                        true,
                        null,
                        VariantState.CREATED
                ));
                return null;
            }
            return null;
        }));
        if (!MocksSettings.exists(classToMock)) {
            MocksSettings.addNewMock(classToMock, new Mock(
                    MockState.CREATED,
                    new ArrayList<>()
            ));
        }
        Constructor<?> constructor = classToMock.getDeclaredConstructors()[0];
        if (constructor == null) {
            System.out.println("There is no accessible constructor");
        }

        Class<?>[] argTypes = constructor.getParameterTypes();
        Object[] args = new Object[argTypes.length];

        return (T) enhancer.create(argTypes, args);
    }

    public static <T> OngoingStubbing<T> when(T methodCall) {
        HashMap<Class, Mock> globalState = MocksSettings.getGlobalState();
        for (Mock mock : globalState.values()) {
            if (mock.getState().equals(MockState.IN_PROGRESS)) {
                mock.setState(MockState.READY);
                for (Variant variant : mock.getVariants()) {
                    if (variant.getState().equals(VariantState.CREATED))
                        variant.setState(VariantState.IN_PROGRESS);
                }
            }
        }
        return new OngoingStubbing<>(methodCall);
    }

    public static void initMocks(Object obj){
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MockAnnotation.class)) {
                field.setAccessible(true);
                try {
                    field.set(obj, API.mock(field.getType()));
                }
                catch (IllegalAccessException e) {
                }
                break;
            }
        }
    }
}
