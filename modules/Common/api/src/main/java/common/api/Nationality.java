package common.api;

public enum Nationality {

    IRISH("Irish", Country.IRELAND),
    AMERICAN("American", Country.USA);

    String name;
    Country country;

    private Nationality(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
