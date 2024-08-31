package Chain;

public interface AuthenticationHandler {
    void setNextHandler(AuthenticationHandler nextHandler);
    String authenticate(String email, String password);
}
