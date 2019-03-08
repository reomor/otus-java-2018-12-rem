package rem.hw08.atm;

import java.util.Map;

public interface AtmDepartment {
    void resetAllAtm(Map<Integer, MoneyStack> atmBalances);

    MoneyStack getBalancesSum();
}
