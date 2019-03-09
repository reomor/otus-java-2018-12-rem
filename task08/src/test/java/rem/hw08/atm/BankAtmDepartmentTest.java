package rem.hw08.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BankAtmDepartmentTest extends AtmTest {
    private AtmDepartmentObservable atmDepartmentObservable;
    private AtmObserver atmObserver;

    @BeforeEach
    public void setUp() {
        super.setUp();
        atmDepartmentObservable = new BankAtmDepartment();
        atmObserver = new BankAtm(setMoneyStack(3));
        atmDepartmentObservable.registerObserver(atmObserver);
    }

    @Test
    void registerObserver() throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = atmDepartmentObservable.getClass().getDeclaredField("atms");
        declaredField.setAccessible(true);
        Map<Integer, AtmObserver> value = (Map<Integer, AtmObserver>)(declaredField.get(atmDepartmentObservable));
        final int sizeBefore = value.size();
        atmDepartmentObservable.registerObserver(new BankAtm());
        final int sizeAfter = value.size();
        assertEquals(sizeBefore, sizeAfter - 1);
    }

    @Test
    void removeObserver() throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = atmDepartmentObservable.getClass().getDeclaredField("atms");
        declaredField.setAccessible(true);
        Map<Integer, AtmObserver> value = (Map<Integer, AtmObserver>)(declaredField.get(atmDepartmentObservable));
        final int sizeBefore = value.size();
        atmDepartmentObservable.removeObserver(atmObserver);
        final int sizeAfter = value.size();
        assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    void resetAllAtm() {
        atmDepartmentObservable.registerObserver(new BankAtm(setMoneyStack(4)));
        final Map<Integer, MoneyStack> newBalances = new HashMap<>();
        final int baseAmount = 10;
        newBalances.put(1, setMoneyStack(baseAmount));
        newBalances.put(2, setMoneyStack(baseAmount));
        atmDepartmentObservable.resetAllAtm(newBalances);
        for (Map.Entry<MoneyPar, Integer> entry : atmDepartmentObservable.getBalancesSum().getStackAsMap().entrySet()) {
            assertEquals((Integer)(baseAmount * newBalances.size()), entry.getValue());
        }
    }

    @Test
    void getBalancesSum() {
        atmDepartmentObservable = new BankAtmDepartment();
        final int baseAmount1 = 4;
        final int baseAmount2 = 6;
        final int baseAmount = baseAmount1 + baseAmount2;
        final BankAtm bankAtm1 = new BankAtm(setMoneyStack(baseAmount1));
        final BankAtm bankAtm2 = new BankAtm(setMoneyStack(baseAmount2));
        atmDepartmentObservable.registerObserver(bankAtm1);
        atmDepartmentObservable.registerObserver(bankAtm2);
        for (Map.Entry<MoneyPar, Integer> entry : atmDepartmentObservable.getBalancesSum().getStackAsMap().entrySet()) {
            final MoneyPar moneyPar = entry.getKey();
            assertEquals(baseAmount,
                    bankAtm1.balanceStack().getStackAsMap().get(moneyPar) + bankAtm2.balanceStack().getStackAsMap().get(moneyPar));
        }
    }
}