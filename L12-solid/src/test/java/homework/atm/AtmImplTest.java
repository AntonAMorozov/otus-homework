package homework.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import homework.atm.banknotes.Banknote;
import homework.atm.impl.AtmImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

class AtmImplTest {

    @Test
    void startingBalanceEqualsZero() {
        Atm atm = getAtmList();
        assertEquals(0, atm.getCurrentBalance());
        System.out.println(atm.getCurrentBalance());
    }

    @Test
    void depositCashList() {
        Atm atm = getAtmList();
        // вносим 4800
        atm.depositCashList(2, 4, 8);
        assertEquals(4800, atm.getCurrentBalance());
    }

    @Test
    void balanceAfterWithDrawCash() {
        Atm atm = getAtmList();
        // вносим 4800
        atm.depositCashList(2, 4, 8);

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
        Atm atm = getAtmList();
        // вносим 4800
        atm.depositCashList(2, 4, 8);

        assertEquals(4800, atm.getCurrentBalance());

        // снимаем 4900
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4900));
        assertEquals("Not enough money!", thrown.getMessage());
    }

    @Test
    void notEnoughBanknotesError() {
        Atm atm = getAtmList();
        atm.depositCashList(5, 0, 7);

        assertEquals(5700, atm.getCurrentBalance());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4900));
        assertEquals("Not enough banknotes!", thrown.getMessage());
    }

    @Test
    void amountMustBeMultipleOfHundredError() {
        Atm atm = getAtmList();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4970));
        assertEquals("The amount must be a multiple of 100", thrown.getMessage());
    }

    private static Atm getAtmList() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote().setDenomination(1000));
        banknotes.add(new Banknote().setDenomination(500));
        banknotes.add(new Banknote().setDenomination(100));
        return new AtmImpl().setBanknotes(banknotes);
    }
}
