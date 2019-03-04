package rem.hw07.atm;

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

class MoneyStackTest extends AtmTest {
    @Test
    void add() {
        final Integer amountToAdd = 600;
        Integer currentFiftyAmount = this.moneyStack.getStack().get(MoneyPar.FIFTY50);
        this.moneyStack.add(MoneyPar.FIFTY50, amountToAdd);
        assertEquals((Integer)(currentFiftyAmount + amountToAdd), moneyStack.getStack().get(MoneyPar.FIFTY50));
    }

    @Test
    void addToStackDirectly() {
        Map<MoneyPar, Integer> stack = this.moneyStack.getStack();
        assertThrows(UnsupportedOperationException.class, () -> stack.put(MoneyPar.FIFTY50, 1));
    }

    @Test
    void getStack() {
        Map<MoneyPar, Integer> stack = this.moneyStack.getStack();
        assertNotNull(stack);
        for (MoneyPar moneyPar : MoneyPar.values()) {
            assertThat(stack.get(moneyPar), IsNot.not(0));
        }
    }

    @Test
    void sum() {
        int expectedSum = 0;
        Map<MoneyPar, Integer> stack = this.moneyStack.getStack();
        for (MoneyPar moneyPar : MoneyPar.values()) {
            expectedSum += moneyPar.getNominal() * stack.get(moneyPar);
        }
        assertEquals(expectedSum, this.moneyStack.sum());
    }
}