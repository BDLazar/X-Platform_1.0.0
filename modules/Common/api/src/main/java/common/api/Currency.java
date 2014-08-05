package common.api;

/**
 * Created by Onis on 11/07/14.
 */
public enum Currency {

    //TODO Create all currencies
    ALL("Albanian Lek", "ALL"),
    EUR("Euro", "EUR"),
    USD("US Dollar","USD"),
    GBP("British Pound","GBP");

    private String name;
    private String ISO;

    Currency(String name, String ISO) {
        this.name = name;
        this.ISO = ISO;
    }

    public String getName() {
        return name;
    }

    public String getISO() {
        return ISO;
    }
}
