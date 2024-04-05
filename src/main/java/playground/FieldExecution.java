package main.java.playground;

import main.java.structure.Bank;
import main.java.structure.Player;

public enum FieldExecution {

    GETMONEY((executor, parameter) -> Bank.getMoney(executor, Double.parseDouble(parameter))),
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
