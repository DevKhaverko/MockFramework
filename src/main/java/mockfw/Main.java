package mockfw;

public class Main {
    public static void main(String[] args) {
        User user = API.mock(User.class);
        API.when(user.getName()).thenReturn("here");
        API.when(user.getNJKef("asd")).thenReturn("rew");
        API.when(user.getNJKef("dsafdf")).thenReturn("333");
        API.when(user.getNJKef("1")).thenThrow(new Exception());
        System.out.println(user.getName());
        System.out.println(user.getNJKef("asd"));
        System.out.println(user.getNJKef("dsafdf"));
        System.out.println(user.getNJKef("1"));
    }
}
