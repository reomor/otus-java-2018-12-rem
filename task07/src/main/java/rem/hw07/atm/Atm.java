package rem.hw07.atm;

public interface Atm {
    MoneyStack get(int amount);
    void put(MoneyStack stack);
    MoneyStack balance();
}
