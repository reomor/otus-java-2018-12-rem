package rem.hw08.atm;

public enum MoneyPar {
    TEN_10(10),
    FIFTY_50(50),
    HUNDRED_100(100),
    TWOHUNDRED_200(200),
    FIVEHUNDRED_500(500),
    THOUSAND_1000(1000),
    TWOTHOUSAND_2000(2000),
    FIVETHOUSAND_5000(5000);

    private int nominal;

    private MoneyPar(int nominal) {
        this.nominal = nominal;
    }

    public Integer getNominal() {
        return nominal;
    }
}
