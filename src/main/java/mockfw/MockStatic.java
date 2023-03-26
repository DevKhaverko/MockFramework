package mockfw;

import javassist.*;
import javassist.util.HotSwapAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockStatic implements AutoCloseable{
    private final List<CtMethod> saveList = new ArrayList<>();
    private CtClass ctClass;
    private static int lastId = 0;
    private Object currentMock;
    private static final List<Object> mockRefs = new ArrayList<>();
    private final Class<?> staticClass;

    MockStatic(Class<?> staticClass){
        this.staticClass = staticClass;
        ClassPool classPool = ClassPool.getDefault();
        try {
            ctClass = classPool.get(staticClass.getName());
            if (ctClass.isFrozen())
                ctClass.defrost();
            CtClass staticMockHelper = classPool.makeClass(
                    "MockStatic" + UUID.randomUUID().toString().replace("-", ""));
            String helperName = staticMockHelper.getName();

            CtMethod[] methods = ctClass.getDeclaredMethods();
            for (CtMethod method : methods) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    continue;
                }

                CtMethod copy = CtNewMethod.copy(method, method.getName(), staticMockHelper, null);
                copy.setModifiers(Modifier.clear(copy.getModifiers(), Modifier.STATIC));
                copy.setBody(null);
                staticMockHelper.addMethod(copy);
            }
            mockRefs.add(API.mock(staticMockHelper.toClass()));
            currentMock = mockRefs.get(lastId);
            for (CtMethod method : methods) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    continue;
                }

                saveList.add(CtNewMethod.copy(method, method.getName(), ctClass, null));
                String body = "((" + helperName + ")mockfw.MockStatic.getMockRef(" + lastId + "))."
                        + method.getName() + "($$);";
                if (!method.getReturnType().getName().equals("void"))
                    body = "return ($r)" + body;

                method.setBody("{" + body + "}");
            }
            lastId++;
            HotSwapAgent.redefine(staticClass, ctClass);
        } catch (NotFoundException | CannotCompileException | IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
    public static Object getMockRef(int id) {
        return mockRefs.get(id);
    }

    @Override
    public void close(){
        ctClass.defrost();
        for (CtMethod ctMethod : ctClass.getDeclaredMethods()){
            if (!Modifier.isStatic(ctMethod.getModifiers())) {
                continue;
            }
            try {
                ctClass.removeMethod(ctMethod);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        saveList.forEach(ctMethod -> {
            try {
                ctClass.addMethod(ctMethod);
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        });
        try {
            HotSwapAgent.redefine(staticClass, ctClass);
        } catch (NotFoundException | IOException | CannotCompileException e) {
            e.printStackTrace();
        }
        mockRefs.remove(currentMock);
    }
}
