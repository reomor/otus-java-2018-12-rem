package rem.hw08;

import rem.hw08.atm.*;

import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        AtmDepartmentObservable atmDepartmentObservable = new BankAtmDepartment();
        MoneyStack moneyStack1 = new MoneyStack();
        moneyStack1.add(MoneyPar.FIFTY_50, 5);
        moneyStack1.add(MoneyPar.HUNDRED_100, 5);
        moneyStack1.add(MoneyPar.TWOHUNDRED_200, 5);
        atmDepartmentObservable.registerObserver(new BankAtm(moneyStack1));
        MoneyStack moneyStack2 = new MoneyStack();
        moneyStack2.add(MoneyPar.HUNDRED_100, 2);
        moneyStack2.add(MoneyPar.TWOHUNDRED_200, 1);
        moneyStack2.add(MoneyPar.THOUSAND_1000, 2);
        moneyStack2.add(MoneyPar.TWOTHOUSAND_2000, 1);
        atmDepartmentObservable.registerObserver(new BankAtm(moneyStack2));
        final MoneyStack balancesSum = atmDepartmentObservable.getBalancesSum();
        balancesSum.print();
        Map<Integer, MoneyStack> moneyStackMap = new HashMap<>();
        moneyStackMap.put(1, new MoneyStack());
        moneyStackMap.put(2, new MoneyStack());
        atmDepartmentObservable.resetAllAtm(moneyStackMap);
        final MoneyStack balancesSumAfterReset = atmDepartmentObservable.getBalancesSum();
        balancesSumAfterReset.print();
    }
}
