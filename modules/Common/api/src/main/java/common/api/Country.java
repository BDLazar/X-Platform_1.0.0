package common.api;


public enum Country
{
    //TODO create all countries
    ALBANIA("Albania","AL","ALB",355,Continent.EUROPE,Currency.ALL),
    ANDORRA("Andorra","AN","AND",376, Continent.EUROPE, Currency.EUR),
    USA("United States","US","USA",376, Continent.EUROPE, Currency.USD),
    IRELAND("Ireland","IE","IRE",353, Continent.EUROPE, Currency.EUR);

    private String name;
    private String ISO2;
    private String ISO3;
    private int code;
    private Continent continent;
    private Currency currency;

    Country(String name, String ISO2, String ISO3, int code, Continent continent, Currency currency) {
        this.name = name;
        this.ISO2 = ISO2;
        this.ISO3 = ISO3;
        this.code = code;
        this.continent = continent;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getISO2() {
        return ISO2;
    }

    public String getISO3() {
        return ISO3;
    }

    public int getCode() {
        return code;
    }

    public Continent getContinent() {
        return continent;
    }

    public Currency getCurrency() {
        return currency;
    }
}