package homework.atm.banknotes;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Banknote {
    private Integer quantity = 0;
}
