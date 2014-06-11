package AuthenticationApi;

public interface IAuthenticationService
{
    public RegisterResponse processRegisterRequest(RegisterRequest signUpRequest);
    public LoginResponse processLoginRequest(LoginRequest loginRequest);
    public ValidateSessionResponse processValidateSessionRequest(ValidateSessionRequest validateRequest);
}
