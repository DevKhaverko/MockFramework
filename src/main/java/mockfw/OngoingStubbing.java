package mockfw;

import java.util.HashMap;

public class OngoingStubbing<T> implements Stubbing<T>{

    Object refToMock;

    public OngoingStubbing(Object refToMock) {
        this.refToMock = refToMock;
    }

    @Override
    public Stubbing<T> thenReturn(T value) {
        HashMap<Class, Mock> globalState = MocksSettings.getGlobalState();
        for (Mock mock:globalState.values()) {
            if (mock.getState().equals(MockState.READY)) {
                for (Variant v : mock.getVariants()) {
                    if (v.getState().equals(VariantState.IN_PROGRESS)) {
                        v.setReturn(true);
                        v.setReturnValues(value);
                        mock.setState(MockState.END);
                        v.setState(VariantState.READY);
                        return null;
                    }
                }
            }
        }
        return this;
    }

    @Override
    public Stubbing<T> thenThrow(Throwable throwable) {
        HashMap<Class, Mock> globalState = MocksSettings.getGlobalState();
        for (Mock mock:globalState.values()) {
            if (mock.getState().equals(MockState.READY)) {
                for (Variant v : mock.getVariants()) {
                    if (v.getState().equals(VariantState.IN_PROGRESS)) {
                        v.setReturn(false);
                        v.setThrowables(throwable);
                        mock.setState(MockState.END);
                        v.setState(VariantState.READY);
                        return null;
                    }
                }
            }
        }
        return this;
    }
}
