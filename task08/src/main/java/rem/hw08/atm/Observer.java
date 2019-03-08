package rem.hw08.atm;

public interface Observer extends Atm {
    void resetBalance(MoneyStack moneyStack);
}
