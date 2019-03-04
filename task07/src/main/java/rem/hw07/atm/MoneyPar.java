package rem.hw07.atm;

public enum MoneyPar {
    TEN10(10),
    FIFTY50(50),
    HUNDRED100(100),
    TWOHUNDRED200(200),
    FIVEHUNDRED500(500),
    THOUSAND1000(1000),
    TWOTHOUSAND2000(2000),
    FIVETHOUSAND5000(5000);

    private int nominal;

    private MoneyPar(int nominal) {
        this.nominal = nominal;
    }

    public long getNominal() {
        return nominal;
    }
}
