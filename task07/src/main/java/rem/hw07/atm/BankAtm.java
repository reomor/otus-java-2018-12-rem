package rem.hw07.atm;

import rem.hw07.atm.exception.ImpossibleToIssue;

import java.util.*;

public class BankAtm implements Atm {
    private Map<MoneyPar, Integer> cells = new HashMap<>();
    private static List<MoneyPar> list = Arrays.asList(MoneyPar.values());

    static {
        list.sort((o1, o2) -> -o1.getNominal().compareTo(o2.getNominal()));
    }

    public BankAtm() {
        for (MoneyPar moneyPar : MoneyPar.values()) {
            cells.put(moneyPar, 0);
        }
    }

    public BankAtm(MoneyStack moneyStack) {
        this();
        moneyStack.getStackAsMap().forEach((moneyPar, amount) -> cells.put(moneyPar, amount));
    }

    @Override
    public MoneyStack get(int sum) throws ImpossibleToIssue {
        if (balanceStack().sum() < sum || sum < 0) {
            throw new ImpossibleToIssue();
        }
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
        reduce(moneyStack);
        return moneyStack;
    }

    private int calculateMaxAmount(int sum, int term) {
        if (sum >= 0) {
            return sum / term;
        } else {
            return 0;
        }
    }

    private void reduce(MoneyStack moneyStack) throws ImpossibleToIssue {
        final Map<MoneyPar, Integer> stackAsMap = moneyStack.getStackAsMap();
        for (Map.Entry<MoneyPar, Integer> entry : stackAsMap.entrySet()) {
            if (entry.getValue() < stackAsMap.get(entry.getKey())) {
                throw new ImpossibleToIssue();
            }
        }
        stackAsMap.forEach((moneyPar, amount) -> {
            final Integer cellsAmount = cells.get(moneyPar);
            cells.put(moneyPar, cellsAmount - amount);
        });
    }

    @Override
    public void put(MoneyStack moneyStack) throws ImpossibleToIssue {
        final Map<MoneyPar, Integer> stackAsMap = moneyStack.getStackAsMap();
        for (Map.Entry<MoneyPar, Integer> entry : stackAsMap.entrySet()) {
            if (entry.getValue() < 0) {
                throw new ImpossibleToIssue();
            }
        }
        stackAsMap.forEach((moneyPar, amount) ->
                cells.put(moneyPar, cells.getOrDefault(moneyPar, 0) + amount));
}

    @Override
    public void put(MoneyPar moneyPar, int amount) throws ImpossibleToIssue {
        if (amount < 0) {
            throw new ImpossibleToIssue();
        }
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
