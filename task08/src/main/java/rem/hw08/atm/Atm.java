package rem.hw08.atm;

import rem.hw08.atm.exception.ImpossibleToIssue;

public interface Atm {
    Integer getId();

    MoneyStack get(int amount) throws ImpossibleToIssue;

    void put(MoneyStack moneyStack) throws ImpossibleToIssue;

    void put(MoneyPar moneyPar, int amount) throws ImpossibleToIssue;

    MoneyStack balanceStack();

    void printBalance();
}
