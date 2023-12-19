package homework.atm;

import static org.junit.jupiter.api.Assertions.*;

import homework.atm.impl.AtmImpl;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

class AtmImplTest {

    @Test
    void startingBalanceEqualsZero() {
        Atm atm = new AtmImpl();
        assertEquals(0, atm.getCurrentBalance());
        System.out.println(atm.getCurrentBalance());
    }

    @Test
    void depositCash() {
        Atm atm = new AtmImpl();
        // вносим 4800
        atm.depositCash(2, 4, 8);
        assertEquals(4800, atm.getCurrentBalance());
    }

    @Test
    void balanceAfterWithDrawCash() {
        Atm atm = new AtmImpl();
        // вносим 4800
        atm.depositCash(2, 4, 8);

        assertEquals(4800, atm.getCurrentBalance());

        // снимаем 3600
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(1000, 2);
        map.put(500, 3);
        map.put(100, 1);

        assertEquals(map, atm.withdrawCash(3600));
        assertEquals(1200, atm.getCurrentBalance());
    }

    @Test
    void notEnoughMoneyError() {
        Atm atm = new AtmImpl();
        // вносим 4800
        atm.depositCash(2, 4, 8);

        assertEquals(4800, atm.getCurrentBalance());

        // снимаем 4900
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4900));
        assertEquals("Not enough money!", thrown.getMessage());
    }

    @Test
    void notEnoughBanknotesError() {
        Atm atm = new AtmImpl();
        atm.depositCash(5, 0, 7);

        assertEquals(5700, atm.getCurrentBalance());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4900));
        assertEquals("Not enough banknotes!", thrown.getMessage());
    }

    @Test
    void amountMustBeMultipleOfHundredError() {
        Atm atm = new AtmImpl();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4970));
        assertEquals("The amount must be a multiple of 100", thrown.getMessage());
    }
}
