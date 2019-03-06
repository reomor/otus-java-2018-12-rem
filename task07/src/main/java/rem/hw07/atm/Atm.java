package rem.hw07.atm;

import rem.hw07.atm.exception.ImpossibleToIssue;

public interface Atm {
    MoneyStack get(int amount) throws ImpossibleToIssue;

    void put(MoneyStack moneyStack) throws ImpossibleToIssue;

    void put(MoneyPar moneyPar, int amount) throws ImpossibleToIssue;

    MoneyStack balanceStack();

    void printBalance();
}
