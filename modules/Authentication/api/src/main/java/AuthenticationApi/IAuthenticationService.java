package AuthenticationApi;

public interface IAuthenticationService
{
    public SignUpResponse processSignUpRequest(SignUpRequest signUpRequest);
    public LoginResponse processLoginRequest(LoginRequest loginRequest);
}
