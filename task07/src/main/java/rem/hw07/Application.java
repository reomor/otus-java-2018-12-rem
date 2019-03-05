package rem.hw07;

import rem.hw07.atm.Atm;
import rem.hw07.atm.BankAtm;
import rem.hw07.atm.MoneyPar;
import rem.hw07.atm.MoneyStack;
import rem.hw07.atm.exception.ImpossibleToIssue;

public class Application {
    public static void main(String[] args) {
        Atm atm = new BankAtm();
        atm.printBalance();
        MoneyStack moneyStack = new MoneyStack();
        moneyStack.add(MoneyPar.FIVEHUNDRED_500, 2);
        moneyStack.add(MoneyPar.FIFTY_50, 4);
        moneyStack.add(MoneyPar.THOUSAND_1000, 5);
        atm.put(moneyStack);
        MoneyStack moneyStackGot = null;
        try {
            moneyStackGot = atm.get(1550);
            System.out.println(moneyStackGot.sum());
        } catch (ImpossibleToIssue impossibleToIssue) {
            System.out.println("Bad sum");
        }
    }
}
