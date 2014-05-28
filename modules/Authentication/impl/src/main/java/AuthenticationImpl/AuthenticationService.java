package AuthenticationImpl;

import AuthenticationApi.*;

import java.util.List;


public class AuthenticationService implements IAuthenticationService
{
    private AuthenticationDAO authenticationDAO;

    public AuthenticationService(AuthenticationDAO authenticationDAO){
        this.authenticationDAO = authenticationDAO;
    }

    @Override
    public RegisterResponse processRegisterRequest(RegisterRequest registerRequest) {

        //build a response
        RegisterResponse signUpResponse = new RegisterResponse();

        //make sure the request is valid
        if(registerRequest.getEmail() == null || registerRequest.getPassword() == null)
        {
            signUpResponse.setSignUpResponseType(RegisterResponseType.INVALID_REGISTER_REQUEST);
            return signUpResponse;
        }

        //check to see if email already exists in database
        boolean emailExists=false;
        if(authenticationDAO.getUserLoginByEmail(registerRequest.getEmail()).size() > 0)
        {
            emailExists =true;
        }

        signUpResponse.setEmail(registerRequest.getEmail());

        if(emailExists)
        {
            signUpResponse.setSignUpResponseType(RegisterResponseType.ALREADY_REGISTERED);
        }
        else
        {
            //create new User Login Data and save it to the database
            UserLoginData newUserLogin = new UserLoginData(registerRequest.getEmail(),registerRequest.getPassword());
            boolean loginSaved = authenticationDAO.save(newUserLogin);
            if(loginSaved)
            {
                signUpResponse.setSignUpResponseType(RegisterResponseType.REGISTER_SUCCESS);
            }
            else
            {
                signUpResponse.setSignUpResponseType(RegisterResponseType.REGISTER_FAILED);
            }
        }

        return signUpResponse;
    }

    @Override
    public LoginResponse processLoginRequest(LoginRequest loginRequest) {


        LoginResponse loginResponse = new LoginResponse();

        //Make sure the request is valid
        if(loginRequest.getPassword() == null || loginRequest.getLoginID() == null )
        {
            loginResponse.setLoginResponseType(LoginResponseType.INVALID_LOGIN_REQUEST);
            return loginResponse;
        }

        //check to see if email and password exists in database and create an appropriate login response
        List<UserLoginData> loginAccounts = authenticationDAO.getUserLoginByEmail(loginRequest.getLoginID());
        loginResponse.setEmail(loginRequest.getLoginID());
        loginResponse.setLoginResponseType(LoginResponseType.LOGIN_FAILED);

        for(UserLoginData UserLogin : loginAccounts)
        {
            if(loginRequest.getLoginID().equals(UserLogin.getEmail()) && loginRequest.getPassword().equals(UserLogin.getPassword()))
            {
                loginResponse.setLoginResponseType(LoginResponseType.LOGIN_SUCCESS);
                String token = generateToken(loginRequest.getLoginID(),loginRequest.getPassword());
                loginResponse.setToken(token);
                break;
            }
        }
        return loginResponse;
    }


    private String generateToken(String loginID, String password) {
        return "abc123";
    }

    private boolean validateToken(String token, String userID) {

        if(token!=null && token.equals("abc123"))
        {
            return true;
        }

        return false;
    }

}
