package AuthenticationImpl;

//import PersistenceApi.IPersistenceService;

import AuthenticationApi.UserLoginData;

import javax.persistence.EntityManager;
import java.util.List;

public class AuthenticationDAO
{
    private EntityManager postgresSession;

    public AuthenticationDAO(EntityManager postgresSession){
        this.postgresSession = postgresSession;
    }

    public boolean save(UserLoginData user) {

        //TODO Catch exceptions here and return accordingly

        postgresSession.persist(user);
        postgresSession.flush();

        return true;
    }

    public List<UserLoginData> getUserLoginByEmail(String email){
        return  postgresSession.createQuery("SELECT ul from UserLoginData ul where ul.email = ?1").setParameter(1, email).getResultList();
    }

}
