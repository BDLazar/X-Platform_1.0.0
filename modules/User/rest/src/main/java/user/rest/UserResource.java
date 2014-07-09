package user.rest;

/**
 * Created by Bianca on 7/9/2014.
 */

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import user.api.IUserService;
import user.api.UserDetails;

import static org.slf4j.LoggerFactory.getLogger;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.*;

@CrossOriginResourceSharing(allowAllOrigins = true) // allows client access to authentication resource from all domains
@Path("/user/")
public class UserResource {

    //region Properties
    private static final Logger LOGGER = getLogger(UserResource.class);
    private static final String ERROR_PROCESSING_REST_REQUEST = "Error processing REST request";
    private static final String FINISHED_PROCESSING_REST_REQUEST = "Finished processing REST request: {}";
    private static final String RECEIVED_REST_REQUEST = "{}: received REST request: {}";
    private static final String TEXT_PLAIN = "text/plain";
    private IUserService userService;

    //endregion

    //region Constructor
    public UserResource(IUserService userService) {
        this.userService = userService;
    }
    //endregion

    //region Get User Profile
    @GET
    @Path("get/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@Context UriInfo uriInfo, @PathParam("userId") String userId, @HeaderParam("token") String token) {
        try {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::getUser", uriInfo.getPath());
            GetUserResponse getUserResponse = new GetUserResponse();
            if (userId == null || userId.equals("")) {
                getUserResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
                getUserResponse.setServerMessage("User id must be specified");
            } else {
                UserDetails userDetails = userService.getUser(userId);
                if (userDetails == null) {
                    getUserResponse.setResponseStatus(ResponseStatus.FAIL);
                    getUserResponse.setServerMessage("User does not exist");
                } else {
                    getUserResponse.setResponseStatus(ResponseStatus.SUCCESS);
                    getUserResponse.setServerMessage("OK");
                    getUserResponse.setUserDetails(userDetails);
                }
            }

            return Response.ok().entity(getUserResponse.toJson()).build();
        } catch (Exception ex) {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        } finally {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }
    //endregion

    //region Create User
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create")
    public Response create(@Context UriInfo uriInfo, UserDetails userDetails) {
        try {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::Register", uriInfo.getPath());
            CreateUserResponse createUserResponse = new CreateUserResponse();

            if (userDetails.getId() == null || userDetails.getId().equals("")) {
                String userId = userService.createUser(userDetails);

                if (userId == null) {
                    createUserResponse.setResponseStatus(ResponseStatus.FAIL);
                    createUserResponse.setServerMessage("Failed to create user details");
                } else {
                    createUserResponse.setResponseStatus(ResponseStatus.SUCCESS);
                    createUserResponse.setServerMessage("OK");
                    createUserResponse.setUserDetails(userService.getUser(userId));
                }
            } else {
                createUserResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
                createUserResponse.setServerMessage("User profile id must be null");
            }

            return Response.ok().entity(createUserResponse.toJson()).build();
        } catch (Exception ex) {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        } finally {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }

    //endregion

    //region Validate
    @GET
    @Path("update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@Context UriInfo uriInfo, @PathParam("id") String id, @HeaderParam("loginID") String userID, @HeaderParam("token") String token) {
        try {
            LOGGER.info(RECEIVED_REST_REQUEST, "Authentication::ValidateUserSession", uriInfo.getPath());

            return Response.ok().entity(null).build();
        } catch (Exception ex) {
            LOGGER.error(ERROR_PROCESSING_REST_REQUEST, ex);
            return createBadRequestResponse(ex);
        } finally {
            LOGGER.info(FINISHED_PROCESSING_REST_REQUEST, uriInfo.getPath());
        }
    }
    //endregion

    //region Bad Response
    private Response createBadRequestResponse(Exception ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).type(TEXT_PLAIN).build();
    }
//endregion*/
}

