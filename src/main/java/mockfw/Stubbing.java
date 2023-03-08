package mockfw;

public interface Stubbing<T> {
    public Stubbing<T> thenReturn(T value);
    public Stubbing<T> thenThrow(Throwable throwable);
}
