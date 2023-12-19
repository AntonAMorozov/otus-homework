package homework.atm.impl;

import static homework.atm.banknotes.FiveHundred.DENOMINATION_500;
import static homework.atm.banknotes.Hundred.DENOMINATION_100;
import static homework.atm.banknotes.Thousand.DENOMINATION_1000;

import homework.atm.Atm;
import homework.atm.banknotes.FiveHundred;
import homework.atm.banknotes.Hundred;
import homework.atm.banknotes.Thousand;
import homework.atm.exception.AtmException;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class AtmImpl implements Atm {

    Thousand thousand = new Thousand();
    FiveHundred fiveHundred = new FiveHundred();
    Hundred hundred = new Hundred();

    @Override
    public int getCurrentBalance() {
        return getAvailableBanknotes().entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    @Override
    public void depositCash(int thousands, int fiveHundreds, int hundreds) {
        thousand.setQuantity(thousand.getQuantity() + thousands);
        fiveHundred.setQuantity(fiveHundred.getQuantity() + fiveHundreds);
        hundred.setQuantity(hundred.getQuantity() + hundreds);
    }

    @Override
    public Map<Integer, Integer> withdrawCash(int amount) {
        Map<Integer, Integer> result = new TreeMap<>();
        if (amount % 100 != 0) {
            throw new AtmException("The amount must be a multiple of 100");
        }
        if (amount > getCurrentBalance()) {
            throw new AtmException("Not enough money!");
        }
        int n1000;
        int n500;
        int n100 = 0;
        int b1000 = amount / DENOMINATION_1000; // сколько 1000 купюр надо
        var quantity1000 = getAvailableBanknotes().get(DENOMINATION_1000); // сколько 1000 есть
        if (quantity1000 - b1000 > 0) {
            n1000 = b1000;
        } else {
            n1000 = quantity1000;
        }
        result.put(DENOMINATION_1000, n1000);
        thousand.setQuantity(thousand.getQuantity() - n1000);
        if (amount - n1000 * DENOMINATION_1000 != 0) {
            int amount2 = amount - n1000 * DENOMINATION_1000;
            int b500 = amount2 / DENOMINATION_500; // сколько 500 купюр надо
            var quantity500 = getAvailableBanknotes().get(DENOMINATION_500); // сколько 500 есть
            if (quantity500 - b500 > 0) {
                n500 = b500;
            } else {
                n500 = quantity500;
            }
            result.put(DENOMINATION_500, n500);
            fiveHundred.setQuantity(fiveHundred.getQuantity() - n500);
            if (amount2 - n500 * DENOMINATION_500 != 0) {
                int amount3 = amount2 - n500 * DENOMINATION_500;
                int b100 = amount3 / DENOMINATION_100; // сколько 100 купюр надо
                var quantity100 = getAvailableBanknotes().get(DENOMINATION_100); // сколько 100 есть
                if (quantity100 - b100 > 0) {
                    n100 = b100;
                } else if (quantity100 - b100 == 0) {
                    n100 = quantity100;
                } else if (quantity100 - b100 < 0) {
                    throw new AtmException("Not enough banknotes!");
                }
                result.put(DENOMINATION_100, n100);
                hundred.setQuantity(hundred.getQuantity() - n100);
            }
        }
        return result;
    }

    private Map<Integer, Integer> getAvailableBanknotes() {
        Map<Integer, Integer> cash = new TreeMap<>();
        cash.put(DENOMINATION_1000, thousand.getQuantity());
        cash.put(DENOMINATION_500, fiveHundred.getQuantity());
        cash.put(DENOMINATION_100, hundred.getQuantity());
        return cash;
    }
}
