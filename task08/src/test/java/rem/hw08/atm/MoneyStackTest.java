package rem.hw08.atm;

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoneyStackTest extends AtmTest {
    @Test
    void add() {
        final Integer amountToAdd = 600;
        Integer currentFiftyAmount = this.moneyStack.getStackAsMap().get(MoneyPar.FIFTY_50);
        this.moneyStack.add(MoneyPar.FIFTY_50, amountToAdd);
        assertEquals((Integer)(currentFiftyAmount + amountToAdd), moneyStack.getStackAsMap().get(MoneyPar.FIFTY_50));
    }

    @Test
    void addToStackDirectly() {
        Map<MoneyPar, Integer> stack = this.moneyStack.getStackAsMap();
        assertThrows(UnsupportedOperationException.class, () -> stack.put(MoneyPar.FIFTY_50, 1));
    }

    @Test
    void getStack() {
        Map<MoneyPar, Integer> stack = this.moneyStack.getStackAsMap();
        assertNotNull(stack);
        for (MoneyPar moneyPar : MoneyPar.values()) {
            assertThat(stack.get(moneyPar), IsNot.not(0));
        }
    }

    @Test
    void sum() {
        int expectedSum = 0;
        Map<MoneyPar, Integer> stack = this.moneyStack.getStackAsMap();
        for (MoneyPar moneyPar : MoneyPar.values()) {
            expectedSum += moneyPar.getNominal() * stack.get(moneyPar);
        }
        assertEquals(expectedSum, this.moneyStack.sum());
    }
}