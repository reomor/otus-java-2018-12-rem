package rem.hw07.atm;

import rem.hw07.atm.exception.ImpossibleToIssue;

public interface Atm {
    MoneyStack get(int amount) throws ImpossibleToIssue;

    void put(MoneyStack stack);

    void put(MoneyPar moneyPar, int amount);

    MoneyStack balanceStack();

    void printBalance();
}
