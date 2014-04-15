package AuthenticationImpl;

import AuthenticationApi.IAuthenticationService;


public class AuthenticationService implements IAuthenticationService
{
    private AuthenticationDAO authenticationDAO;


    public AuthenticationService(AuthenticationDAO authenticationDAO)
    {
        this.authenticationDAO = authenticationDAO;
    }

    @Override
    public boolean validateCredentials(String username, String password)
    {
        if(username == null || password == null)
        {
            return false;
        }
        else if (username.equals("Onisim") && password.equals("Csadi"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getEmail()
    {
        return "";
    }
}
