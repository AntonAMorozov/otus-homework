package homework.atm.impl;

import homework.atm.Atm;
import homework.atm.banknotes.Banknote;
import homework.atm.exception.AtmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@RequiredArgsConstructor
public class AtmImpl implements Atm {

    List<Banknote> banknotes;

    @Override
    public void depositCashList(int... cash) {
        for (int i = 0; i < banknotes.size(); i++) {
            banknotes.get(i).setQuantity(banknotes.get(i).getQuantity() + cash[i]);
        }
    }

    @Override
    public int getCurrentBalance() {
        return getAvailableBanknotes().entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    @Override
    public Map<Integer, Integer> withdrawCash(int amount) {
        Map<Integer, Integer> result = new TreeMap<>();
        if (amount % banknotes.getLast().getDenomination() != 0) {
            throw new AtmException(
                    "The amount must be a multiple of " + banknotes.getLast().getDenomination());
        }
        if (amount > getCurrentBalance()) {
            throw new AtmException("Not enough money!");
        }
        int cash;

        cash = getFromAtm(amount, result, banknotes.getFirst());
        if (amount - cash * banknotes.getFirst().getDenomination() != 0) {
            int amount2 = amount - cash * banknotes.getFirst().getDenomination();
            int cash2 = getFromAtm(amount2, result, banknotes.get(1));
            if (amount2 - cash2 * banknotes.get(1).getDenomination() != 0) {
                int amount3 = amount2 - cash2 * banknotes.get(1).getDenomination();
                if (amount3
                        > banknotes.getLast().getQuantity()
                                * banknotes.getLast().getDenomination()) {
                    throw new AtmException("Not enough banknotes!");
                }
                getFromAtm(amount3, result, banknotes.getLast());
            }
        }
        return result;
    }

    private int getFromAtm(int amount, Map<Integer, Integer> result, Banknote banknote) {
        int banknoteQuantity;
        int cashBanknote = amount / banknote.getDenomination();
        var quantity = getAvailableBanknotes().get(banknote.getDenomination());
        if (quantity - cashBanknote > 0) {
            banknoteQuantity = cashBanknote;
        } else {
            banknoteQuantity = quantity;
        }
        result.put(banknote.getDenomination(), banknoteQuantity);
        banknote.setQuantity(banknote.getQuantity() - banknoteQuantity);
        return banknoteQuantity;
    }

    private Map<Integer, Integer> getAvailableBanknotes() {
        Map<Integer, Integer> cash = new TreeMap<>();
        for (Banknote banknote : banknotes) {
            cash.put(banknote.getDenomination(), banknote.getQuantity());
        }
        return cash;
    }
}
