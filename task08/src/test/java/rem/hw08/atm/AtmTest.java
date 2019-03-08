package rem.hw08.atm;

import org.junit.jupiter.api.BeforeEach;

public abstract class AtmTest {
    protected MoneyStack moneyStack = null;
    protected final int baseAmount = 10;
    @BeforeEach
    public void setUp() {
        moneyStack = new MoneyStack();
        moneyStack.add(MoneyPar.TEN_10, baseAmount);
        moneyStack.add(MoneyPar.FIFTY_50, baseAmount);
        moneyStack.add(MoneyPar.HUNDRED_100, baseAmount);
        moneyStack.add(MoneyPar.TWOHUNDRED_200, baseAmount);
        moneyStack.add(MoneyPar.FIVEHUNDRED_500, baseAmount);
        moneyStack.add(MoneyPar.THOUSAND_1000, baseAmount);
        moneyStack.add(MoneyPar.TWOTHOUSAND_2000, baseAmount);
        moneyStack.add(MoneyPar.FIVETHOUSAND_5000, baseAmount);
    }
}
