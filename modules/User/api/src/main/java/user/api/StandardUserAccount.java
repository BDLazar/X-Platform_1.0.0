package user.api;

import org.codehaus.jackson.JsonNode;
        import org.codehaus.jackson.map.ObjectMapper;
        import org.codehaus.jackson.map.ObjectWriter;
        import org.codehaus.jackson.node.ObjectNode;
        import javax.persistence.Entity;
        import javax.persistence.PrimaryKeyJoinColumn;
        import javax.persistence.Table;
        import java.util.Iterator;

@Entity
@Table(name="STANDARD_USER_ACCOUNT")
@PrimaryKeyJoinColumn(name="id")
public class StandardUserAccount extends UserAccount{

    //region Properties
    String testProperty;
    //endregion

    //region Constructors
    public StandardUserAccount() {
        super(UserAccountType.STANDARD);
    }

    public StandardUserAccount(String testProperty) {
        super(UserAccountType.STANDARD);
        this.testProperty = testProperty;
    }
    //endregion

    //region Getters & Setters
    public String getTestProperty() {
        return testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty;
    }
    //endregion

    //region Parsers
    @Override
    public ObjectNode toJson(){

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            //serialise superclass
            JsonNode superJsonObj = super.toJson();

            //Serialise subclass
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonString = objectWriter.writeValueAsString(this);
            JsonNode subJsonObj = jsonMapper.readTree(jsonString);

            //merge subclass and superclass
            JsonNode jsonObj = mergeJsonNodes(superJsonObj,subJsonObj);

            return (ObjectNode)jsonObj;

        } catch (Exception ex){

            return null;
        }

    }
    private static JsonNode mergeJsonNodes(JsonNode mainNode, JsonNode updateNode){
        Iterator<String> fieldNames = updateNode.getFieldNames();

        while (fieldNames.hasNext()) {

            String fieldName = fieldNames.next();
            JsonNode jsonNode = mainNode.get(fieldName);

            // if field exists and is an embedded object
            if (jsonNode != null && jsonNode.isObject())
            {
                mergeJsonNodes(jsonNode, updateNode.get(fieldName));
            }
            else
            {
                if (mainNode instanceof ObjectNode) {
                    // Overwrite field
                    JsonNode value = updateNode.get(fieldName);
                    ((ObjectNode) mainNode).put(fieldName, value);
                }
            }
        }

        return mainNode;
    }
    //endregion

    //region Business Methods
    @Override
    public int updateFrom(UserAccount userAccountUpdates)
    {
        int updatesAmount = 0;

        //try cast to Standard User Profile and apply the updates
        if(userAccountUpdates.getUserAccountType() == UserAccountType.STANDARD)
        {
            //Update superclass UserAccount class
            super.updateFrom(userAccountUpdates);

            StandardUserAccount standardUserAccount = (StandardUserAccount) userAccountUpdates;

            String newTestProperty = standardUserAccount.getTestProperty();

            if (newTestProperty != null) {this.testProperty = newTestProperty; updatesAmount++;}

        }

        return updatesAmount;
    }
    //endregion
}
