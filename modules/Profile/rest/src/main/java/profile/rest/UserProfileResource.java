package profile.rest;

import profile.api.*;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@CrossOriginResourceSharing(allowAllOrigins = true) // allows client access to authentication resource from all domains
@Path("/user-profile/")
public class UserProfileResource {

    //region Properties
    private static final Logger LOGGER = getLogger(UserProfileResource.class);
    private static final String ERROR_PROCESSING_REST_REQUEST = "Error processing REST request";
    private static final String FINISHED_PROCESSING_REST_REQUEST = "Finished processing REST request: {}";
    private static final String RECEIVED_REST_REQUEST = "{}: received REST request: {}";
    private static final String TEXT_PLAIN = "text/plain";
    private IUserProfileService userProfileService;

    //endregion

    //region Constructor
    public UserProfileResource(IUserProfileService userProfileService)
    {
        this.userProfileService = userProfileService;
    }
    //endregion

    //region Get User Profile
    @GET
    @Path("get/{profileId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@Context UriInfo uriInfo, @PathParam("profileId") String profileId, @HeaderParam("token") String token)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "Profile::getUserProfile", uriInfo.getPath());
            GetUserProfileResponse getUserProfileResponse = new GetUserProfileResponse();
            if(profileId == null || profileId.equals(""))
            {
                getUserProfileResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
                getUserProfileResponse.setServerMessage("Profile id must be specified");
            }
            else
            {
                UserProfile userProfile = userProfileService.getUserProfile(profileId);
                if(userProfile == null)
                {
                    getUserProfileResponse.setResponseStatus(ResponseStatus.FAIL);
                    getUserProfileResponse.setServerMessage("Profile does not exist");
                }
                else
                {
                    getUserProfileResponse.setResponseStatus(ResponseStatus.SUCCESS);
                    getUserProfileResponse.setServerMessage("OK");
                    getUserProfileResponse.setUserProfile(userProfile);
                }
            }

            return Response.ok().entity(getUserProfileResponse.toJson()).build();
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

    //region Create User Profile
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create")
    public Response create(@Context UriInfo uriInfo, UserProfile userProfile)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "Profile::Register", uriInfo.getPath());
            CreateUserProfileResponse createUserProfileResponse = new CreateUserProfileResponse();

            if(userProfile.getId() == null || userProfile.getId().equals(""))
            {
                String profileId = userProfileService.createUserProfile(userProfile);

                if(profileId == null)
                {
                    createUserProfileResponse.setResponseStatus(ResponseStatus.FAIL);
                    createUserProfileResponse.setServerMessage("Failed to create user profile");
                }
                else
                {
                    createUserProfileResponse.setResponseStatus(ResponseStatus.SUCCESS);
                    createUserProfileResponse.setServerMessage("OK");
                    createUserProfileResponse.setUserProfile(userProfileService.getUserProfile(profileId));
                }
            }
            else
            {
                createUserProfileResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
                createUserProfileResponse.setServerMessage("User profile id must be null");
            }

            return Response.ok().entity(createUserProfileResponse.toJson()).build();
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
    @Path("update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@Context UriInfo uriInfo, @PathParam("id") String id, @HeaderParam("loginID") String userID, @HeaderParam("token") String token)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "Authentication::ValidateUserSession", uriInfo.getPath());

            return Response.ok().entity(null).build();
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
    //endregion*/

}