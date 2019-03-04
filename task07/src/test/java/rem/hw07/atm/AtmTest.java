package rem.hw07.atm;

import org.junit.jupiter.api.BeforeEach;

public abstract class AtmTest {
    protected MoneyStack moneyStack = null;
    protected final int baseAmount = 10;
    @BeforeEach
    public void setUp() {
        moneyStack = new MoneyStack();
        moneyStack.add(MoneyPar.TEN10, baseAmount);
        moneyStack.add(MoneyPar.FIFTY50, baseAmount);
        moneyStack.add(MoneyPar.HUNDRED100, baseAmount);
        moneyStack.add(MoneyPar.TWOHUNDRED200, baseAmount);
        moneyStack.add(MoneyPar.FIVEHUNDRED500, baseAmount);
        moneyStack.add(MoneyPar.THOUSAND1000, baseAmount);
        moneyStack.add(MoneyPar.TWOTHOUSAND2000, baseAmount);
        moneyStack.add(MoneyPar.FIVETHOUSAND5000, baseAmount);
    }
}
