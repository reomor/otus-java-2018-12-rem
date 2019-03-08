package rem.hw08.atm;

import rem.hw08.atm.exception.ImpossibleToIssue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BankAtm implements AtmObserver {
    private static AtomicInteger idGenerator = new AtomicInteger(1);
    private static List<MoneyPar> list = Arrays.asList(MoneyPar.values());

    private Integer id;
    private Map<MoneyPar, Integer> cells = new HashMap<>();

    static {
        list.sort((o1, o2) -> -o1.getNominal().compareTo(o2.getNominal()));
    }

    public Integer getId() {
        return id;
    }

    public BankAtm() {
        this.id = idGenerator.getAndIncrement();
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

    @Override
    public void resetBalance(MoneyStack moneyStack) {
        final Map<MoneyPar, Integer> stackAsMap = moneyStack.getStackAsMap();
        stackAsMap.forEach((moneyPar, amount) -> {
            cells.put(moneyPar, amount);
        });
    }
}
