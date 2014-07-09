package user.api;

public interface IUserService {
    public UserDetails getUser(String userId);
    public String createUser(UserDetails userDetails);
    public boolean deleteUser(String userId);
    public boolean modifyUser(String userId, UserDetails userDetails);
}
