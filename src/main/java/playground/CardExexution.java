package playground;


import structure.Bank;
import structure.Player;

public enum CardExexution {

    GETMONEY((executor, parameter) -> Bank.getMoney(executor, Double.parseDouble(parameter))),
    LOOSEMONEY((executor, parameter) -> Bank.looseMoney(executor, Double.parseDouble(parameter))),
    CHANGEFIELD((executor, parameter) -> {
        int value = Integer.parseInt(parameter);
        if (value >= 0) {
            executor.setCurrentField(value);
        } else {
            executor.setCurrentFieldRelative(value);
        }
        return "";
    }),
    GETCARD((executor, parameter) -> {
        if (parameter.equals(Constants.EVENT)) {
            return executor.chooseEventCard();
        } else {
            return executor.chooseCommunityCard();
        }
    }),
    GOPRISON((executor, parameter) -> executor.goPrison()),
    FREEPRISON((executor, parameter) -> {
        return "";
    }),
    RENOVATE((executor, parameter) -> {
        String[] prices = parameter.split(Constants.SPLIT_REGEX);
        return Bank.renovate(executor, Double.parseDouble(prices[0]), Double.parseDouble(prices[1]));
    }),
    PAYORGETCARD((executor, parameter) -> {
        return "";
    }),
    MONEYFROMEVERYONE((executor, parameter) ->
            Bank.moneyFormEveryone(executor, Double.parseDouble(parameter)));

    private final SpecialCardEffect specialCardEffect;

    CardExexution(SpecialCardEffect specialCardEffect) {
        this.specialCardEffect = specialCardEffect;
    }

    public String execute(Player executor, String parameter) {
        return specialCardEffect.execute(executor, parameter);
    }

    private static class Constants {
        private static final String SPLIT_REGEX = ";";
        private static final String EVENT = "event";
    }
}
