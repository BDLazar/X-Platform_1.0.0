package AuthenticationApi;

public interface IAuthenticationService
{
    public boolean validateCredentials(String username, String password);
    public String getEmail();
}
