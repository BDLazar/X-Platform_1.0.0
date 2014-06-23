package persistence.api;

public interface IPersistenceService
{
    public Object getDBClient();
    public boolean testDBConnection();
}
