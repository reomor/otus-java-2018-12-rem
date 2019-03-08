package rem.hw08.atm;

import org.junit.jupiter.api.BeforeEach;

public abstract class AtmTest {
    protected MoneyStack moneyStack = null;
    private final int BASE_AMOUNT = 2;

    @BeforeEach
    public void setUp() {
        this.moneyStack = setMoneyStack(BASE_AMOUNT);
    }

    public MoneyStack setMoneyStack(int[] amounts) {
        MoneyStack moneyStack = new MoneyStack();
        if (amounts.length < 8) {
            throw new RuntimeException("Bad money stack amount setting");
        }
        moneyStack.add(MoneyPar.TEN_10, amounts[0]);
        moneyStack.add(MoneyPar.FIFTY_50, amounts[1]);
        moneyStack.add(MoneyPar.HUNDRED_100, amounts[2]);
        moneyStack.add(MoneyPar.TWOHUNDRED_200, amounts[3]);
        moneyStack.add(MoneyPar.FIVEHUNDRED_500, amounts[4]);
        moneyStack.add(MoneyPar.THOUSAND_1000, amounts[5]);
        moneyStack.add(MoneyPar.TWOTHOUSAND_2000, amounts[6]);
        moneyStack.add(MoneyPar.FIVETHOUSAND_5000, amounts[7]);
        return moneyStack;
    }

    public MoneyStack setMoneyStack(int baseAmount) {
        MoneyStack moneyStack = new MoneyStack();
        moneyStack.add(MoneyPar.TEN_10, baseAmount);
        moneyStack.add(MoneyPar.FIFTY_50, baseAmount);
        moneyStack.add(MoneyPar.HUNDRED_100, baseAmount);
        moneyStack.add(MoneyPar.TWOHUNDRED_200, baseAmount);
        moneyStack.add(MoneyPar.FIVEHUNDRED_500, baseAmount);
        moneyStack.add(MoneyPar.THOUSAND_1000, baseAmount);
        moneyStack.add(MoneyPar.TWOTHOUSAND_2000, baseAmount);
        moneyStack.add(MoneyPar.FIVETHOUSAND_5000, baseAmount);
        return moneyStack;
    }
}
