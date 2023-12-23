package homework.atm;

import java.util.Map;

public interface Atm {

    void depositCashList(int... cash);

    int getCurrentBalance();

    Map<Integer, Integer> withdrawCash(int amount);
}
