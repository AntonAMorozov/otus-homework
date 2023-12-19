package homework.atm;

import java.util.Map;

public interface Atm {
    int getCurrentBalance();

    void depositCash(int thousands, int fiveHundreds, int hundreds);

    Map<Integer, Integer> withdrawCash(int amount);
}
