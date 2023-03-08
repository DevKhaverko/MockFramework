package mockfw;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Variant {
    private String method;
    private List<Object> args;
    private Object returnValues;

    private boolean isReturn;

    private VariantState state;

    public VariantState getState() {
        return state;
    }

    public void setState(VariantState state) {
        this.state = state;
    }

    public Variant(String method, List<Object> args, Object returnValues, boolean isReturn, Throwable throwables, VariantState state) {
        this.method = method;
        this.args = args;
        this.returnValues = returnValues;
        this.isReturn = isReturn;
        this.throwables = throwables;
        this.state = state;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean returned) {
        this.isReturn = returned;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<Object> args) {
        this.args = args;
    }

    public Object getReturnValues() {
        return returnValues;
    }

    public void setReturnValues(Object returnValues) {
        this.returnValues = returnValues;
    }

    public Throwable getThrowables() {
        return throwables;
    }

    public void setThrowables(Throwable throwables) {
        this.throwables = throwables;
    }

    private Throwable throwables;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variant Variant = (Variant) o;
        return Objects.equals(method, Variant.method) && Objects.equals(args, Variant.args) && Objects.equals(returnValues, Variant.returnValues) && Objects.equals(throwables, Variant.throwables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, args, returnValues, throwables);
    }
}
