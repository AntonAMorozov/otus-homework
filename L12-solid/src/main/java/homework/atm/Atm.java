package homework.atm;

import homework.atm.banknotes.StackOfBanknotes;
import java.util.List;
import java.util.Map;

public interface Atm {

    void depositCash(List<StackOfBanknotes> stackOfBanknotes);

    int getCurrentBalance();

    Map<Integer, Integer> withdrawCash(int amount);
}
