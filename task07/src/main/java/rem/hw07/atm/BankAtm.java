package rem.hw07.atm;

import rem.hw07.atm.exception.ImpossibleToIssue;

import java.util.*;

public class BankAtm implements Atm {
    private Map<MoneyPar, Integer> cells = new HashMap<>();

    public BankAtm() {
        for(MoneyPar moneyPar : MoneyPar.values()) {
            cells.put(moneyPar, 0);
        }
    }

    public BankAtm(MoneyStack moneyStack) {
        this();
        moneyStack.getStackAsMap().forEach((moneyPar, amount) -> cells.put(moneyPar, amount));
    }

    @Override
    public MoneyStack get(int sum) throws ImpossibleToIssue {
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
        if (divident != 0) {
            throw new ImpossibleToIssue();
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
        stack.getStackAsMap().forEach((moneyPar, amount) -> cells.put(moneyPar, cells.getOrDefault(moneyPar, 0) + amount));
    }

    @Override
    public void put(MoneyPar moneyPar, int amount) {
        cells.put(moneyPar, cells.getOrDefault(moneyPar, 0) + amount);
    }

    @Override
    public MoneyStack balanceStack() {
        MoneyStack moneyStack = new MoneyStack();
        cells.forEach(moneyStack::add);
        return moneyStack;
    }

    public void printBalance() {
        MoneyStack balance = this.balanceStack();
        System.out.println("==================");
        balance.getStackAsMap().forEach((moneyPar, amount) ->
                System.out.println(
                        String.format("%6d: %10d", moneyPar.getNominal(), amount)
                )
        );
        System.out.println("==================");
    }
}
