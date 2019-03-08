package rem.hw08.atm;

import rem.hw08.atm.exception.IncorrectMoneyAmount;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MoneyStack {
    private Map<MoneyPar, Integer> stack = new HashMap<>();

    public MoneyStack() {
        for (MoneyPar moneyPar : MoneyPar.values()) {
            stack.put(moneyPar, 0);
        }
    }

    public void add(MoneyPar moneyPar, int amount) throws IncorrectMoneyAmount {
        if (amount < 0) {
            throw new IncorrectMoneyAmount();
        }
        stack.put(moneyPar, stack.getOrDefault(moneyPar, 0) + amount);
    }

    public void add(MoneyStack moneyStack) throws IncorrectMoneyAmount{
        final Map<MoneyPar, Integer> stackAsMap = moneyStack.getStackAsMap();
        for (Map.Entry<MoneyPar, Integer> entry : stackAsMap.entrySet()) {
            if (entry.getValue() < 0) {
                throw new IncorrectMoneyAmount();
            }
        }
        stackAsMap.forEach((moneyPar, amount) ->
                stack.put(moneyPar, stack.getOrDefault(moneyPar, 0) + amount));
    }

    public Map<MoneyPar, Integer> getStackAsMap() {
        return Collections.unmodifiableMap(stack);
    }

    public int sum() {
        return stack.entrySet().stream()
                .map(entry -> entry.getKey().getNominal() * entry.getValue())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
