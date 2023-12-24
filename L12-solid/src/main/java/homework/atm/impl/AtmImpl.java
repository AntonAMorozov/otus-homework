package homework.atm.impl;

import homework.atm.Atm;
import homework.atm.banknotes.StackOfBanknotes;
import homework.atm.exception.AtmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtmImpl implements Atm {

    private final BanknotesStorageImpl storage;

    @Override
    public void depositCash(List<StackOfBanknotes> cash) {
        for (int i = 0; i < storage.stackOfBanknotes().size(); i++) {
            storage.stackOfBanknotes()
                    .get(i)
                    .setQuantity(storage.stackOfBanknotes().get(i).getQuantity()
                            + cash.get(i).getQuantity());
        }
    }

    @Override
    public int getCurrentBalance() {
        return storage.getAvailableBanknotes().entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    @Override
    public Map<Integer, Integer> withdrawCash(int amount) {
        Map<Integer, Integer> result = new TreeMap<>();
        var banknotesFromStorage = storage.stackOfBanknotes();
        if (amount % banknotesFromStorage.getLast().getDenomination() != 0) {
            throw new AtmException("The amount must be a multiple of "
                    + banknotesFromStorage.getLast().getDenomination());
        }
        if (amount > getCurrentBalance()) {
            throw new AtmException("Not enough money!");
        }
        int cash;

        cash = getFromAtm(amount, result, banknotesFromStorage.getFirst());
        if (amount - cash * banknotesFromStorage.getFirst().getDenomination() != 0) {
            int amount2 = amount - cash * banknotesFromStorage.getFirst().getDenomination();
            int cash2 = getFromAtm(amount2, result, banknotesFromStorage.get(1));
            if (amount2 - cash2 * banknotesFromStorage.get(1).getDenomination() != 0) {
                int amount3 = amount2 - cash2 * banknotesFromStorage.get(1).getDenomination();
                if (amount3
                        > banknotesFromStorage.getLast().getQuantity()
                                * banknotesFromStorage.getLast().getDenomination()) {
                    throw new AtmException("Not enough banknotes!");
                }
                getFromAtm(amount3, result, banknotesFromStorage.getLast());
            }
        }
        return result;
    }

    private int getFromAtm(int amount, Map<Integer, Integer> result, StackOfBanknotes stackOfBanknotes) {
        int banknoteQuantity;
        int cashBanknote = amount / stackOfBanknotes.getDenomination();
        var quantity = storage.getAvailableBanknotes().get(stackOfBanknotes.getDenomination());
        if (quantity - cashBanknote > 0) {
            banknoteQuantity = cashBanknote;
        } else {
            banknoteQuantity = quantity;
        }
        result.put(stackOfBanknotes.getDenomination(), banknoteQuantity);
        stackOfBanknotes.setQuantity(stackOfBanknotes.getQuantity() - banknoteQuantity);
        return banknoteQuantity;
    }
}
