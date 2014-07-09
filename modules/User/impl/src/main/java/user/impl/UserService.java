package user.impl;

import user.api.IUserService;
import user.api.UserDetails;

/**
 * Created by Bianca on 7/9/2014.
 */
public class UserService implements IUserService {

    private  UserDAO userDAO;

    public UserService(UserDAO userDAO) {

        this.userDAO = userDAO;
    }

    @Override
    public UserDetails getUser(String userId) {
        return userDAO.getUser(userId);
    }

    @Override
    public String createUser(UserDetails userDetails) {

        if(userDetails.getId() == null || userDetails.getId().equals(""))
        {
            userDetails.setId(generateNewUserId());
            if(userDAO.saveUserDetails(userDetails))
            {
                return userDetails.getId();
            }
        }

        return null;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public boolean modifyUser(String userId, UserDetails userDetails) {
        return false;
    }


    private String generateNewUserId()
    {
        long nextID = userDAO.countUsers() + 1;
        return "USR_ID_"+ nextID;
    }
}
