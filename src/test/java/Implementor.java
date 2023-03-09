public class Implementor implements TestInterface{
    @Override
    public Integer get(Integer integer) {
        return 1;
    }

    @Override
    public Boolean getBool() {
        return true;
    }
}
