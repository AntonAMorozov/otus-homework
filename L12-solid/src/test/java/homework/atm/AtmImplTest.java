package homework.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import homework.atm.banknotes.StackOfBanknotes;
import homework.atm.impl.AtmImpl;
import homework.atm.impl.BanknotesStorageImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

class AtmImplTest {

    @Test
    void startingBalanceEqualsZero() {
        Atm atm = getAtm();
        assertEquals(0, atm.getCurrentBalance());
        System.out.println(atm.getCurrentBalance());
    }

    @Test
    void depositCashList() {
        Atm atm = getAtm();
        // вносим 4800
        atm.depositCash(getCashList(2, 4, 8));
        assertEquals(4800, atm.getCurrentBalance());
    }

    @Test
    void balanceAfterWithDrawCash() {
        Atm atm = getAtm();
        // вносим 4800
        atm.depositCash(getCashList(2, 4, 8));

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
        Atm atm = getAtm();
        // вносим 4800
        atm.depositCash(getCashList(2, 4, 8));

        assertEquals(4800, atm.getCurrentBalance());

        // снимаем 4900
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4900));
        assertEquals("Not enough money!", thrown.getMessage());
    }

    @Test
    void notEnoughBanknotesError() {
        Atm atm = getAtm();
        atm.depositCash(getCashList(5, 0, 7));

        assertEquals(5700, atm.getCurrentBalance());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4900));
        assertEquals("Not enough banknotes!", thrown.getMessage());
    }

    @Test
    void amountMustBeMultipleOfHundredError() {
        Atm atm = getAtm();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> atm.withdrawCash(4970));
        assertEquals("The amount must be a multiple of 100", thrown.getMessage());
    }

    private static Atm getAtm() {
        List<StackOfBanknotes> stackOfBanknotes = new ArrayList<>();
        stackOfBanknotes.add(new StackOfBanknotes().setDenomination(1000));
        stackOfBanknotes.add(new StackOfBanknotes().setDenomination(500));
        stackOfBanknotes.add(new StackOfBanknotes().setDenomination(100));
        BanknotesStorageImpl banknotesStorageImpl = new BanknotesStorageImpl(stackOfBanknotes);
        return new AtmImpl(banknotesStorageImpl);
    }

    private static List<StackOfBanknotes> getCashList(int firstQuantity, int secondQuantity, int thirdQuantity) {
        List<StackOfBanknotes> stackOfBanknotes = new ArrayList<>();
        stackOfBanknotes.add(new StackOfBanknotes().setDenomination(1000).setQuantity(firstQuantity));
        stackOfBanknotes.add(new StackOfBanknotes().setDenomination(500).setQuantity(secondQuantity));
        stackOfBanknotes.add(new StackOfBanknotes().setDenomination(100).setQuantity(thirdQuantity));
        return stackOfBanknotes;
    }
}
