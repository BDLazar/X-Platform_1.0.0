package AuthenticationRest;

import AuthenticationApi.IAuthenticationService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;


@Path("authenticate")
public class AuthenticationResource {

    private static final Logger LOGGER = getLogger(AuthenticationResource.class);
    private static final String ERROR_PROCESSING_REST_REQUEST = "Error processing REST request";
    private static final String FINISHED_PROCESSING_REST_REQUEST = "Finished processing REST request: {}";
    private static final String RECEIVED_REST_REQUEST = "{}: received REST request: {}";
    private static final String TEXT_PLAIN = "text/plain";
    private IAuthenticationService authenticationService;


    public AuthenticationResource(IAuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getSample(@Context UriInfo uriInfo)
    {
        boolean grantAccess = authenticationService.validateCredentials("Onis", "Csadi");

        try {
            LOGGER.info(RECEIVED_REST_REQUEST, "getSample", uriInfo.getPath());

            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(1)).build();
            String message;
            if(grantAccess)
            {
                message = "valid credentials";
            }
            else
            {
                message = "in-valid credentials";
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonNode = mapper.createObjectNode();

            jsonNode.put("Uri", uri.toASCIIString());
            jsonNode.put("Message", message);
            jsonNode.put("Email", authenticationService.getEmail());

            return Response.ok().entity(jsonNode).build();
        }
        catch (Exception ex)
        {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        } finally {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }

    private Response createBadRequestResponse(Exception ex)
    {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).type(TEXT_PLAIN).build();
    }
}