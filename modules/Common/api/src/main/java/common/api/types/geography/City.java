package common.api.types.geography;

public enum City {

    DUBLIN("Dublin","DUB",Country.IRELAND),
    NEW_YORK("New York","NY",Country.USA);

    String name;
    String code;
    Country country;

    City(String name, String code, Country country) {
        this.name = name;
        this.code = code;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Country getCountry() {
        return country;
    }
}
