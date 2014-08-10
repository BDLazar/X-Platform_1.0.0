package user.rest;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import user.api.IUserService;
import user.api.UserAccount;
import user.api.UserProfile;

import static org.slf4j.LoggerFactory.getLogger;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@CrossOriginResourceSharing(allowAllOrigins = true) // allows client access to authentication resource from all domains
@Path("/")
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
    public UserResource(IUserService userService)
    {
        this.userService = userService;
    }
    //endregion

    //region User Account

    //region Create User Account
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create/userAccount")
    public Response createUserAccount(@Context UriInfo uriInfo, UserAccount userAccount)
    {
        try
        {

            LOGGER.info(RECEIVED_REST_REQUEST, "User::CreateUserAccount", uriInfo.getPath());

            UserAccount userAccountCreated = userService.createUserAccount(userAccount);

            if(userAccountCreated != null)
            {
                return Response.ok().entity(userAccountCreated.toJson()).build();
            }
            else
            {
                return Response.ok().entity("Unable to create user account").build();
            }

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

    //region Update User Account
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("update/userAccount")
    public Response updateUserAccount(@Context UriInfo uriInfo, UserAccount userAccountUpdates)
    {
        try
        {

            LOGGER.info(RECEIVED_REST_REQUEST, "User::UpdateUserAccount", uriInfo.getPath());

            UserAccount updatedUserAccount = userService.updateUserAccount(userAccountUpdates);

            if(updatedUserAccount != null)
            {
                return Response.ok().entity(updatedUserAccount.toJson()).build();
            }
            else
            {
                return Response.ok().entity("Unable to update user account").build();
            }
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

    //region Get User Account
    @GET
    @Path("get/userAccount/{userAccountId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getUserAccount(@Context UriInfo uriInfo,
                                   @PathParam("userAccountId") Long userAccountId,
                                   @HeaderParam("withProfiles") boolean withProfiles,
                                   @HeaderParam("authToken") String authToken)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::GetUserAccount", uriInfo.getPath());
            UserAccount existingUserAccount = userService.getUserAccount(userAccountId,withProfiles);

            if(existingUserAccount != null)
            {
                return Response.ok().entity(existingUserAccount.toJson()).build();
            }
            else
            {
                return Response.ok().entity("Unable to retrieve user account").build();
            }
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

    //region Delete User Account
    @DELETE
    @Path("delete/userAccount/{userAccountId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteUserAccount(@Context UriInfo uriInfo,
                                      @PathParam("userAccountId") Long userAccountId,
                                      @HeaderParam("authToken") String authToken)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::GetUserAccount", uriInfo.getPath());

            if(userService.deleteUserAccount(userAccountId))
            {
                return Response.ok().entity("Deleted user account successfully").build();
            }
            else
            {
                return Response.ok().entity("Unable to delete user account").build();
            }
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

    //endregion

    //region User Profile

    //region Create User Profile
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create/userProfile")
    public Response createUserProfile(@Context UriInfo uriInfo,
                                      @HeaderParam("authToken") String authToken,
                                      @HeaderParam("userAccountId") Long userAccountId,
                                      UserProfile newUserProfile)
    {
        try
        {

            LOGGER.info(RECEIVED_REST_REQUEST, "User::CreateUserProfile", uriInfo.getPath());

            UserProfile userProfileCreated = userService.createUserProfile(userAccountId, newUserProfile);

            if(userProfileCreated != null)
            {
                return Response.ok().entity(userProfileCreated.toJson()).build();
            }
            else
            {
                return Response.ok().entity("Unable to create user profile").build();
            }

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

    //region Update User Profile
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("update/userProfile")
    public Response updateUserProfile(@Context UriInfo uriInfo, UserProfile userProfileUpdates)
    {
        try
        {

            LOGGER.info(RECEIVED_REST_REQUEST, "User::CreateUserProfile", uriInfo.getPath());

            UserProfile updatedUserProfile = userService.updateUserProfile(userProfileUpdates);

            if(updatedUserProfile != null)
            {
                return Response.ok().entity(updatedUserProfile.toJson()).build();
            }
            else
            {
                return Response.ok().entity("Unable to update user profile").build();
            }
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

    //region Get User Profile
    @GET
    @Path("get/userProfile/{userProfileId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserProfile(@Context UriInfo uriInfo,
                                   @PathParam("userProfileId") Long userProfileId,
                                   @HeaderParam("authToken") String authToken)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::GetUserAccount", uriInfo.getPath());
            UserProfile existingUserProfile = userService.getUserProfile(userProfileId);

            if(existingUserProfile != null)
            {
                return Response.ok().entity(existingUserProfile.toJson()).build();
            }
            else
            {
                return Response.ok().entity("Unable to retrieve user profile").build();
            }
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

    //region delete User Profile
    @DELETE
    @Path("delete/userProfile/{userProfileId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteUserAccount(@Context UriInfo uriInfo,
                                      @PathParam("userProfileId") Long userProfileId,
                                      @HeaderParam("userAccountId") Long userAccountId,
                                      @HeaderParam("authToken") String authToken)
    {
        try
        {
            LOGGER.info(RECEIVED_REST_REQUEST, "User::DeleteUserProfile", uriInfo.getPath());

            if(userService.deleteUserProfile(userAccountId,userProfileId))
            {
                return Response.ok().entity("Deleted user profile successfully").build();
            }
            else
            {
                return Response.ok().entity("Unable to delete user profile").build();
            }
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

    //endregion

    //region Bad Response
    private Response createBadRequestResponse(Exception ex){
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).type(TEXT_PLAIN).build();
    }
    //endregion

}