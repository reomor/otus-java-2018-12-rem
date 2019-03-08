package rem.hw08.atm;

public interface AtmObserver extends Atm {
    void resetBalance(MoneyStack moneyStack);
}
