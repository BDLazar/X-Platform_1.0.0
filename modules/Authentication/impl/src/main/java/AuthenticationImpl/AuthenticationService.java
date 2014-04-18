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
    public SignUpResponse processSignUpRequest(SignUpRequest signUpRequest) {

        //build a response
        SignUpResponse signUpResponse = new SignUpResponse();

        //make sure the request is valid
        if(signUpRequest.getEmail() == null || signUpRequest.getPassword() == null)
        {
            signUpResponse.setSignUpResponseType(SignUpResponseType.INVALID_SIGNUP_REQUEST);
            return signUpResponse;
        }

        //check to see if email already exists in database
        boolean emailExists=false;
        if(authenticationDAO.getUserLoginByEmail(signUpRequest.getEmail()).size() > 0)
        {
            emailExists =true;
        }

        signUpResponse.setEmail(signUpRequest.getEmail());

        if(emailExists)
        {
            signUpResponse.setSignUpResponseType(SignUpResponseType.ALREADY_REGISTERED);
        }
        else
        {
            //create new User Login Data and save it to the database
            UserLoginData newUserLogin = new UserLoginData(signUpRequest.getEmail(),signUpRequest.getPassword());
            boolean loginSaved = authenticationDAO.save(newUserLogin);
            if(loginSaved)
            {
                signUpResponse.setSignUpResponseType(SignUpResponseType.SIGNUP_SUCCESS);
            }
            else
            {
                signUpResponse.setSignUpResponseType(SignUpResponseType.SIGNUP_FAILED);
            }
        }

        return signUpResponse;
    }

    @Override
    public LoginResponse processLoginRequest(LoginRequest loginRequest) {

        //Make sure the request is valid
        LoginResponse loginResponse = new LoginResponse();
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
                break;
            }
        }
        return loginResponse;
    }

}
