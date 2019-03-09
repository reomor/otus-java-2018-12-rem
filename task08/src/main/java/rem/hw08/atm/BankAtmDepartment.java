package rem.hw08.atm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankAtmDepartment implements AtmDepartmentObservable {
    private Map<Integer, AtmObserver> atms = new HashMap<>();

    @Override
    public void registerObserver(AtmObserver atmObserver) {
        atms.put(atmObserver.getId(), atmObserver);
    }

    @Override
    public void removeObserver(AtmObserver atmObserver) {
        atms.remove(atmObserver.getId());
    }

    @Override
    public void resetAllAtm(Map<Integer, MoneyStack> atmBalances) {
        atms.forEach((atmId, atmObserver) ->
                atmBalances.computeIfPresent(atmId, (atmBalanceId, atmBalance) -> {
                    atmObserver.resetBalance(atmBalance);
                    return atmBalance;
                }));
    }

    @Override
    public MoneyStack getBalancesSum() {
        final List<MoneyStack> moneyStacks = atms.entrySet().stream()
                .map(atmObserverEntry -> atmObserverEntry.getValue().balanceStack())
                .collect(Collectors.toList());
        MoneyStack moneyStack = new MoneyStack();
        moneyStacks.forEach(moneyStack::add);
        return moneyStack;
    }
}
