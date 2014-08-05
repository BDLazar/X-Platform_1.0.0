package user.rest;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import user.api.IUserAccountService;
import user.api.UserAccount;

import static org.slf4j.LoggerFactory.getLogger;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@CrossOriginResourceSharing(allowAllOrigins = true) // allows client access to authentication resource from all domains
@Path("/")
public class UserAccountResource {

    //region Properties
    private static final Logger LOGGER = getLogger(UserAccountResource.class);
    private static final String ERROR_PROCESSING_REST_REQUEST = "Error processing REST request";
    private static final String FINISHED_PROCESSING_REST_REQUEST = "Finished processing REST request: {}";
    private static final String RECEIVED_REST_REQUEST = "{}: received REST request: {}";
    private static final String TEXT_PLAIN = "text/plain";
    private IUserAccountService userService;

    //endregion

    //region Constructor
    public UserAccountResource(IUserAccountService userService)
    {
        this.userService = userService;
    }
    //endregion

    //region Get User Account
    @GET
    @Path("get/userAccount/{userAccountId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserAccount(@Context UriInfo uriInfo,@PathParam("userAccountId") Long userAccountId, @HeaderParam("authToken") String authToken)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::GetUserAccount", uriInfo.getPath());
            UserAccount userAccount = userService.getUserAccount(userAccountId);
            return Response.ok().entity(userAccount.toJson()).build();
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

    //region Create User Account
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create/userAccount")
    public Response createUserAccount(@Context UriInfo uriInfo, UserAccount userAccount)
    {
        try
        {
            String response = "";
            LOGGER.info(RECEIVED_REST_REQUEST, "User::CreateUserAccount", uriInfo.getPath());

            if(userService.createUserAccount(userAccount))
            {
                response = "user Account created";
            }
            else
            {
                response = "failed to create user account";
            }
            return Response.ok().entity(response).build();
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