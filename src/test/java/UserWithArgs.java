public class UserWithArgs {
    String name = "fsdfds";

    public UserWithArgs(String name) {

    }
    public String getName() {
        return name;
    }

    public String getSomethingInteresting(String a, String b) {
        return "regfdg";
    }

    public String getThrowable(String a, String b) throws IllegalArgumentException {
        return "regfdg";
    }
}
