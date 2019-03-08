package rem.hw08.atm;

public interface Observable extends AtmDepartment {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void resetAllAtm();
    MoneyStack getBalancesSum();
}
