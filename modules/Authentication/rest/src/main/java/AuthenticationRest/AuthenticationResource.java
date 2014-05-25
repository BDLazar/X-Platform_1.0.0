package AuthenticationRest;

import AuthenticationApi.*;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@CrossOriginResourceSharing(allowAllOrigins = true) // allows client access to authentication resource from all domains
@Path("/")
public class AuthenticationResource {

    private static final Logger LOGGER = getLogger(AuthenticationResource.class);
    private static final String ERROR_PROCESSING_REST_REQUEST = "Error processing REST request";
    private static final String FINISHED_PROCESSING_REST_REQUEST = "Finished processing REST request: {}";
    private static final String RECEIVED_REST_REQUEST = "{}: received REST request: {}";
    private static final String TEXT_PLAIN = "text/plain";
    private IAuthenticationService authenticationService;
    private IAuthenticationSerializer authenticationSerializer;


    public AuthenticationResource(IAuthenticationService authenticationService, IAuthenticationSerializer authenticationSerializer)
    {
        this.authenticationService = authenticationService;
        this.authenticationSerializer = authenticationSerializer;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("login")
    public Response login(@Context UriInfo uriInfo, LoginRequest loginRequest)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "login", uriInfo.getPath());
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

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("signup")
    public Response signUp(@Context UriInfo uriInfo, SignUpRequest signUpRequest)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "signup", uriInfo.getPath());
            SignUpResponse signUpResponse = authenticationService.processSignUpRequest(signUpRequest);
            return Response.ok().entity(signUpResponse.toJson()).build();
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

    private Response createBadRequestResponse(Exception ex){
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).type(TEXT_PLAIN).build();
    }

    //region get sample
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("getSample")
    public Response getSample(@Context UriInfo uriInfo)
    {

        try {
            LOGGER.info(RECEIVED_REST_REQUEST, "getSample", uriInfo.getPath());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf("sub_path")).build();
            String message = "I got your get request, this is the response";

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonNode = mapper.createObjectNode();

            jsonNode.put("Uri", uri.toASCIIString());
            jsonNode.put("Message", message);


            return Response.ok().entity(jsonNode).build();
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


}