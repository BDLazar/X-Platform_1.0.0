package AuthenticationRest;

import AuthenticationApi.*;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@CrossOriginResourceSharing(allowAllOrigins = true) // allows client access to authentication resource from all domains
@Path("/")
public class AuthenticationResource {

    //region Properties
    private static final Logger LOGGER = getLogger(AuthenticationResource.class);
    private static final String ERROR_PROCESSING_REST_REQUEST = "Error processing REST request";
    private static final String FINISHED_PROCESSING_REST_REQUEST = "Finished processing REST request: {}";
    private static final String RECEIVED_REST_REQUEST = "{}: received REST request: {}";
    private static final String TEXT_PLAIN = "text/plain";
    private IAuthenticationService authenticationService;
    private IAuthenticationSerializer authenticationSerializer;
    //endregion

    //region Constructor
    public AuthenticationResource(IAuthenticationService authenticationService, IAuthenticationSerializer authenticationSerializer)
    {
        this.authenticationService = authenticationService;
        this.authenticationSerializer = authenticationSerializer;
    }
    //endregion

    //region Login
    @GET
    @Path("login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Context UriInfo uriInfo, @HeaderParam("loginID") String loginID, @HeaderParam("password") String password)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "Authentication::Login", uriInfo.getPath());
            LoginRequest loginRequest = new LoginRequest(loginID, password);
            LoginResponse loginResponse = authenticationService.processLoginRequest(loginRequest);
            return Response.ok().entity(loginResponse.toJson()).build();
        }
        catch (Exception ex)
        {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        }
        finally
        {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }
    //endregion

    //region Register
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("register")
    public Response register(@Context UriInfo uriInfo, RegisterRequest registerRequest)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "Authentication::Register", uriInfo.getPath());
            RegisterResponse registerResponse = authenticationService.processRegisterRequest(registerRequest);
            return Response.ok().entity(registerResponse.toJson()).build();
        }
        catch (Exception ex)
        {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        }
        finally
        {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }

    //endregion

    //region Validate
    @GET
    @Path("validate-user-session")
    @Produces({MediaType.APPLICATION_JSON})
    public Response validateUserSession(@Context UriInfo uriInfo, @HeaderParam("loginID") String userID, @HeaderParam("token") String token)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "Authentication::ValidateUserSession", uriInfo.getPath());
            ValidateSessionRequest validateRequest = new ValidateSessionRequest(userID,token);
            ValidateSessionResponse validateResponse = authenticationService.processValidateSessionRequest(validateRequest);
            return Response.ok().entity(validateResponse.toJson()).build();
        }
        catch (Exception ex)
        {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        }
        finally
        {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }
    //endregion

    //region Bad Response
    private Response createBadRequestResponse(Exception ex){
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).type(TEXT_PLAIN).build();
    }
    //endregion

}