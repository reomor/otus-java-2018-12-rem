package rem.hw08.atm;

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import rem.hw08.atm.exception.ImpossibleToIssue;
import rem.hw08.atm.exception.IncorrectMoneyAmount;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BankAtmTest extends AtmTest {
    private Atm atm;

    @BeforeEach
    public void setUpAtm() throws ImpossibleToIssue {
        atm = new BankAtm();
        atm.put(moneyStack);
    }

    @Test
    void checkSetUp() {
        Map<MoneyPar, Integer> stack = atm.balanceStack().getStackAsMap();
        for (MoneyPar moneyPar : MoneyPar.values()) {
            assertThat(stack.get(moneyPar), IsNot.not(0));
        }
    }

    @ParameterizedTest(name = "#{index} testAddMoneyParToAtm({arguments})")
    @CsvSource({
            "TWOHUNDRED_200, 10",
            "TWOTHOUSAND_2000, 50",
            "FIVETHOUSAND_5000, 100",
    })
    void putMoneyPar(String moneyParString, int amount) throws ImpossibleToIssue {
        MoneyPar moneyPar = MoneyPar.valueOf(moneyParString);
        assertNotNull(moneyPar);
        final int currentAmount = moneyStack.getStackAsMap().get(moneyPar);
        atm.put(moneyPar, amount);
        assertEquals((Integer)(currentAmount + amount), atm.balanceStack().getStackAsMap().get(moneyPar));
    }

    @ParameterizedTest(name = "#{index} testAddNegativeMoneyParAmountToAtm({arguments})")
    @CsvSource({
            "TWOHUNDRED_200, -10",
            "TWOTHOUSAND_2000, -50",
            "FIVETHOUSAND_5000, -100",
    })
    void putNegativeMoneyParAmount(String moneyParString, int amount) throws ImpossibleToIssue {
        MoneyPar moneyPar = MoneyPar.valueOf(moneyParString);
        assertNotNull(moneyPar);
        assertThrows(ImpossibleToIssue.class, () -> atm.put(moneyPar, amount));
    }

    @Test
    void putMoneyStack() throws ImpossibleToIssue {
        final int currentSum = atm.balanceStack().sum();
        // add the same stack again
        atm.put(moneyStack);
        final int actualSum = atm.balanceStack().sum();
        assertEquals(currentSum * 2, actualSum);
    }

    @Test
    void putMoneyStackWithNegativeAmount() {
        final MoneyStack moneyStack = new MoneyStack();
        moneyStack.add(MoneyPar.FIVEHUNDRED_500, -1);
        assertThrows(IncorrectMoneyAmount.class, () -> atm.put(moneyStack));
    }

    @Test
    void getBalanceStack() {
        final Map<MoneyPar, Integer> stackAsMap = new BankAtm().balanceStack().getStackAsMap();
        for (MoneyPar moneyPar : MoneyPar.values()) {
            assertEquals((Integer) 0, stackAsMap.get(moneyPar));
        }
    }

    @ParameterizedTest(name = "#{index} testGetMoneyByMinAmountOfBanknotes({arguments})")
    @CsvSource({
            "1550, 3",
            "0, 0",
            "100, 1",
            "4990, 10", //2000 x 2, 500 x 1, 200 x 2, 50 x 1, 10 x 4
            "550, 2"
    })
    void getMoneyFrom(int sum, int amount) throws ImpossibleToIssue {
        final int sumBefore = atm.balanceStack().sum();
        final MoneyStack moneyStack = atm.get(sum);
        final int sumAfter = atm.balanceStack().sum();
        assertEquals(sumAfter + sum, sumBefore);
        final int moneySum = moneyStack.sum();
        assertEquals(moneySum, sum);
        final int moneyAmount = moneyStack.getStackAsMap().values().stream().mapToInt(value -> value).sum();
        assertEquals(amount, moneyAmount);
    }

    @ParameterizedTest()
    @MethodSource("moneyStackProvider")
    void getMoneyFromWhenNotEnoughMoneyInSomeCells(MoneyStack expectedMoneyStack, int sum) throws ImpossibleToIssue {
        final MoneyStack moneyStack = setMoneyStack(new int[]{1, 1, 4, 1, 1, 2, 1, 0});
        atm = new BankAtm(moneyStack);
        final Map<MoneyPar, Integer> actualMoneyStackAsMap = atm.get(sum).getStackAsMap();
        final Map<MoneyPar, Integer> expectedMoneyStackAsMap = expectedMoneyStack.getStackAsMap();
        for (Map.Entry<MoneyPar, Integer> entry : expectedMoneyStackAsMap.entrySet()) {
            if (entry.getValue() > 0) {
                assertEquals(entry.getValue(), actualMoneyStackAsMap.get(entry.getKey()));
            }
        }
    }

    private static Stream<Arguments> moneyStackProvider() {
        MoneyStack moneyStack1 = new MoneyStack();
        moneyStack1.add(MoneyPar.FIFTY_50, 1);
        moneyStack1.add(MoneyPar.HUNDRED_100, 2);
        moneyStack1.add(MoneyPar.TWOHUNDRED_200, 1);
        MoneyStack moneyStack2 = new MoneyStack();
        moneyStack2.add(MoneyPar.HUNDRED_100, 2);
        moneyStack2.add(MoneyPar.TWOHUNDRED_200, 1);
        moneyStack2.add(MoneyPar.THOUSAND_1000, 2);
        moneyStack2.add(MoneyPar.TWOTHOUSAND_2000, 1);
        return Stream.of(
                Arguments.of(moneyStack1, 450),
                Arguments.of(moneyStack2, 4400)
        );
    }

    @ParameterizedTest(name = "#{index} testGetMoneyWithImpossibleAmount({arguments})")
    @CsvSource({
            "1001", "999999995", "5"
    })
    void getMoneyWithException(int amount) {
        assertThrows(ImpossibleToIssue.class, () -> atm.get(amount));
    }
}