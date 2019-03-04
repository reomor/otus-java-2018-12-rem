package rem.hw07.atm;

import java.util.*;
import java.util.function.Function;

public class BankAtm implements Atm {
    private Map<MoneyPar, Integer> cells = new HashMap<>();

    public BankAtm() {
        for(MoneyPar moneyPar : MoneyPar.values()) {
            cells.put(moneyPar, 0);
        }
    }

    @Override
    public MoneyStack get(int sum) {
        List<MoneyPar> list = Arrays.asList(MoneyPar.values());
        list.sort((o1, o2) -> -o1.getNominal().compareTo(o2.getNominal()));
        int divident = sum;
        MoneyStack moneyStack = new MoneyStack();
        for (MoneyPar moneyPar : list) {
            int currentMoneyParAmount = calculateMaxAmount(divident, moneyPar.getNominal());
            if (currentMoneyParAmount > 0) {
                moneyStack.add(moneyPar, currentMoneyParAmount);
                divident = divident - currentMoneyParAmount * moneyPar.getNominal();
            }
        }
        return moneyStack;
    }

    private int calculateMaxAmount(int sum, int term) {
        if (sum >= 0) {
            return sum / term;
        } else {
            return 0;
        }
    }

    @Override
    public void put(MoneyStack stack) {
        stack.getStack().forEach((moneyPar, amount) -> cells.put(moneyPar, amount));
    }

    @Override
    public MoneyStack balance() {
        MoneyStack moneyStack = new MoneyStack();
        cells.forEach(moneyStack::add);
        return moneyStack;
    }
}
