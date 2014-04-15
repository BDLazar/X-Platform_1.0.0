package AuthenticationImpl;

//import PersistenceApi.IPersistenceService;

import AuthenticationApi.User;

import javax.persistence.EntityManager;
import java.util.List;

public class AuthenticationDAO
{
    EntityManager postgresEM;

    public AuthenticationDAO(EntityManager postgresEM)
    {
        this.postgresEM = postgresEM;
    }

    public void add(User user) {
        postgresEM.persist( user );
        postgresEM.flush();
    }

    public List<User> getAll()
    {
        return postgresEM.createQuery( "select d from User d", User.class ).getResultList();
    }

    public void deleteAll()
    {
        postgresEM.createQuery( "delete from User" ).executeUpdate();
        postgresEM.flush();
    }

    public EntityManager getEntityManager()
    {
        return postgresEM;
    }



}
