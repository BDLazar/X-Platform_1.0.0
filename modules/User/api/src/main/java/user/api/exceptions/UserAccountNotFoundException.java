package user.api.exceptions;

public class UserAccountNotFoundException extends Exception{

    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
