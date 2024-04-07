package playground;

import structure.Bank;
import structure.Player;

public enum FieldExecution {

    GETMONEY((executor, parameter) -> {
        if (parameter.equals("freeMoney")) {
            return Bank.centerMoney(executor);
        } else {
            return Bank.getMoney(executor, Double.parseDouble(parameter));
        }
    }),
    LOOSEMONEY((executor, parameter) -> Bank.looseMoney(executor, Double.parseDouble(parameter))),
    GOPRISON((executor, ignored) -> executor.goPrison()),
    GETCARD((executor, parameter) -> {
        if (parameter.equals(Constants.EVENT)) {
            return executor.chooseEventCard();
        } else {
            return executor.chooseCommunityCard();
        }
    }),
    NOTHING((executor, parameter) -> "");

    private final SpecialCardEffect specialCardEffect;

    FieldExecution(SpecialCardEffect specialCardEffect) {
        this.specialCardEffect = specialCardEffect;
    }

    public String execute(Player executor, String parameter) {
        return specialCardEffect.execute(executor, parameter);
    }

    private static class Constants {
        private static final String EVENT = "event";
    }
}
