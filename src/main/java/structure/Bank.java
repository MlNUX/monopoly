package structure;

import exception.CommandException;
import playground.BuyableField;
import playground.Street;

public final class Bank {

    private static final String TRANSFER_MESSAGE = "%s -> %s : DM %s";
    private static final String MONEY_ERROR = "you have not enough money!";
    private static final String BANK = "Bank";
    private static final String DOUBLE_FORMAT = "%.2f";
    private static final String CENTER_IS_EMPTY = "center is empty";
    private static double center = 0;
    private static final double BAIL = Double.parseDouble(GameManager.getProperties("prisonBail"));
    private static final String CENTER = "Center";

    private Bank() {

    }

    public static String transferMoney(Player sender, Player receiver, double money) {
        receiver.addMoney(money);
        sender.removeMoney(money);
        return TRANSFER_MESSAGE.formatted(sender.getName(), receiver.getName(), String.format(DOUBLE_FORMAT, money));
    }

    public static String buyStreet(Player target, BuyableField field) throws CommandException {
        if (field.getPrice() > target.getMoney()) {
            throw new CommandException(MONEY_ERROR);
        }
        target.removeMoney(field.getPrice());
        target.addRealEstate(field);
        field.setOwner(target);
        return TRANSFER_MESSAGE.formatted(target.getName(), BANK, String.format(DOUBLE_FORMAT, field.getPrice()));
    }

    public static String buyHouse(Player target, Street street) throws CommandException {
        if (target.getMoney() < street.getHousePrice()) {
            throw new CommandException(MONEY_ERROR);
        }
        target.removeMoney(street.getHousePrice());
        target.addRealEstate(street);
        return TRANSFER_MESSAGE.formatted(target.getName(), BANK, String.format(DOUBLE_FORMAT, street.getHousePrice()));
    }

    public static String getMoney(Player receiver, double money) {
        receiver.addMoney(money);
        return TRANSFER_MESSAGE.formatted(BANK, receiver.getName(), String.format(DOUBLE_FORMAT, money));
    }

    public static String looseMoney(Player sender, double money) {
        sender.removeMoney(money);
        center += money;
        return TRANSFER_MESSAGE.formatted(sender.getName(), CENTER, String.format(DOUBLE_FORMAT, money));
    }

    public static String renovate(Player target, double priceHouse, double priceHotel) {
        double payedMoney = priceHouse * target.getHouseAmmount()
                + priceHotel * target.getHotelAmmount();
        target.removeMoney(payedMoney);
        return TRANSFER_MESSAGE.formatted(target.getName(), CENTER, String.format(DOUBLE_FORMAT, payedMoney));
    }

    public static String centerMoney(Player target) {
        if (center > 0) {
            double tempMoney = center;
            target.addMoney(center);
            center = 0;
            return TRANSFER_MESSAGE.formatted(CENTER, target.getName(), String.format(DOUBLE_FORMAT, tempMoney));
        } else {
            return CENTER_IS_EMPTY;
        }
    }

    public static String moneyFormEveryone(Player receiver, double money) {
        return "";
    }

    public static String payBail(Player target) throws CommandException {
        if (target.getMoney() >= BAIL) {
            target.removeMoney(BAIL);
            target.setInJail(false);
            return TRANSFER_MESSAGE.formatted(target.getName(), CENTER, String.format(DOUBLE_FORMAT, BAIL));
        }
        throw new CommandException(MONEY_ERROR);
    }
}
