package enums;

public enum Currency {
    DOLLAR("Dollar"),
    POUND("Pound"),
    RUPEE("Rupee");

    private String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
