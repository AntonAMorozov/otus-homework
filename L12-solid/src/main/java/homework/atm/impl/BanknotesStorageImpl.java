package homework.atm.impl;

import homework.atm.BanknotesStorage;
import homework.atm.banknotes.StackOfBanknotes;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public record BanknotesStorageImpl(List<StackOfBanknotes> stackOfBanknotes) implements BanknotesStorage {

    @Override
    public Map<Integer, Integer> getAvailableBanknotes() {
        Map<Integer, Integer> availableBanknotes = new TreeMap<>();
        for (StackOfBanknotes stackOfBanknotes : this.stackOfBanknotes) {
            availableBanknotes.put(stackOfBanknotes.getDenomination(), stackOfBanknotes.getQuantity());
        }
        return availableBanknotes;
    }
}
