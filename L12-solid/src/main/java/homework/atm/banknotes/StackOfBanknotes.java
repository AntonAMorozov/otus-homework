package homework.atm.banknotes;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class StackOfBanknotes {
    private int denomination;
    private int quantity = 0;
}
