package structure;


import command.PayBailCommand;
import exception.CommandException;
import exception.InputArgumentException;
import playground.BuyableField;
import playground.Field;
import playground.SpecialField;
import playground.Street;
import playground.Trainstation;
import realestate.Hotel;
import realestate.House;
import realestate.RealEstate;
import util.Outputs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Player {

    private static final int FIELD_AMMOUNT = 40;
    private static final String TRY_ERROR = "you have no more try!";
    private static final String DICE_MESSAGE = "You dices a %d and a %d! Your now on field '%s'!";
    private static final String OWNER_MESSAGE = "'%s's owner is %s!";
    private static final String BUY_MESSAGE = "Do you want to buy '%s' for DM %s?";
    private static final String BUY_ERROR = "you can't buy this field!";
    private static final String COMPLETE_STEETSET_ERROR = "you need the full street set to buy houses!";
    private static final String ARGS_ERROR = "wrong Argument input!";
    private static final String TURN_ERROR = "you have to dice before you end your turn!";
    private static final String DEBT_ERROR = "first you have to pay off your debts!";
    private static final String DOUBLE_FORMAT = "%.2f";
    private static final String SUCCESS_BUY_MESSAGE = "'%s' is now your Street!";
    private static final String NOBODY_STRING = "nobody";
    private static final String JAIL_MESSAGE = "%s is now in jail!";
    private static final String PAY_BAIL_MESSAGE = "use %s to pay bail";
    private final String name;
    private List<BuyableField> streets;
    private final String playerSymbol;
    private final Account account;
    private int currentField;
    private final GameManager manager;
    private List<RealEstate> realEstates;
    private boolean hasTry;
    private boolean hasGivenUp;
    private boolean inJail;

    public Player(String name, String playerSymbol, double startCapital, GameManager manager) {
        this.name = name;
        this.playerSymbol = playerSymbol;
        account = new Account(startCapital);
        streets = new ArrayList<>();
        currentField = 0;
        this.manager = manager;
        realEstates = new ArrayList<>();
        hasTry = true;
        hasGivenUp = false;
        inJail = false;
    }

    public String getName() {
        return name;
    }

    public void setCapital(double capital) {
        account.setValue(capital);
    }

    public void addMoney(double money) {
        account.setValue(account.getValue() + money);
    }

    public void removeMoney(double money) {
        account.setValue(account.getValue() - money);
    }

    public void addRealEstate(BuyableField field) {
        streets.add(field);
    }

    public Field getCurrentField() {
        return manager.getPlayGround()[currentField];
    }

    public boolean hasGivenUp() {
        return hasGivenUp;
    }

    public List<BuyableField> getStreets() {
        return streets;
    }

    public int getHouseAmmount() {
        return (int) realEstates.stream().filter(realEstate -> realEstate.getClass() == House.class).count();
    }

    public int getHotelAmmount() {
        return (int) realEstates.stream().filter(realEstate -> realEstate.getClass() == Hotel.class).count();
    }

    public void setCurrentField(int currentField) {
        this.currentField = currentField;
        if (currentField == 0) {
            SpecialField los = (SpecialField) manager.getPlayGround()[0];
            addMoney(Double.parseDouble(los.getParameter()));
        }
    }

    public double getMoney() {
        return account.getValue();
    }

    public void setCurrentFieldRelative(int value) {
        currentField = Math.floorMod(currentField + value, FIELD_AMMOUNT);
    }

    public String makeNextMove() throws CommandException {
        if (hasTry && !inJail) {
            StringBuilder builder = new StringBuilder();
            RandomGenerator random = new Random();
            int firstDice = random.nextInt(6) + 1;
            int secondDice = random.nextInt(6) + 1;
            setCurrentFieldRelative(firstDice + secondDice);
            if (firstDice != secondDice) {
                hasTry = false;
            }
            builder.append(DICE_MESSAGE.formatted(firstDice, secondDice, getCurrentField().getName())).append(System.lineSeparator());
            if (getCurrentField().getClass() == Street.class || getCurrentField().getClass() == Trainstation.class) {
                BuyableField field = (BuyableField) getCurrentField();
                if (field.getOwner() == null) {
                    builder.append(OWNER_MESSAGE.formatted(field.getName(), NOBODY_STRING)).append(System.lineSeparator());
                    builder.append(BUY_MESSAGE.formatted(field.getName(), String.format(DOUBLE_FORMAT, field.getPrice())));
                } else if (!streets.contains(field) && !field.getOwner().isInJail()) {
                    builder.append(OWNER_MESSAGE.formatted(field.getName(), field.getOwner().name)).append(System.lineSeparator());
                    //set nicht richtig!
                    if (field.getOwner().hasCompleteSet(field)) {
                        builder.append(Bank.transferMoney(this, field.getOwner(), 2 * field.getRent()));
                    } else {
                        builder.append(Bank.transferMoney(this, field.getOwner(), field.getRent()));
                    }
                } else {
                    builder.append(OWNER_MESSAGE.formatted(field.getName(), field.getOwner().name));
                }
            } else {
                SpecialField field = (SpecialField) getCurrentField();
                builder.append(field.execute(this));
            }
            return builder.toString();
        } else if (hasTry) {
            RandomGenerator random = new Random();
            int firstDice = random.nextInt(6) + 1;
            int secondDice = random.nextInt(6) + 1;
            if (firstDice != secondDice) {
                hasTry = false;
                return "looser!";
            } else {
                inJail = false;
                hasTry = false;
                return "winner!";
            }
        }
        throw new CommandException(TRY_ERROR);
    }

    private boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public String buyStreet() throws CommandException {
        if (getCurrentField().getClass() == Street.class || getCurrentField().getClass() == Trainstation.class) {
            StringBuilder builder = new StringBuilder();
            BuyableField field = (BuyableField) getCurrentField();
            if (field.getOwner() == null) {
                builder.append(SUCCESS_BUY_MESSAGE.formatted(field.getName())).append(System.lineSeparator());
                builder.append(Bank.buyStreet(this, field));
                return builder.toString();
            }
        }
        throw new CommandException(BUY_ERROR);
    }

    public void endTurn() throws CommandException {
        if (!hasTry) {
            if (getMoney() < 0) {
                throw new CommandException(DEBT_ERROR);
            }
            hasTry = true;
            manager.changeActivePlayer();
            return;
        }
        throw new CommandException(TURN_ERROR);
    }

    public String chooseEventCard() {
        return manager.chooseEventCard(this);
    }

    public String chooseCommunityCard() {
        return manager.chooseCommunityCard(this);
    }

    private boolean hasCompleteSet(BuyableField field) {
        List<Integer> streetSet = manager.getStreetSets().stream().filter(integers -> integers.contains(field.getFieldIndex())).findFirst().get();
        for (int i : streetSet) {
            if (!streets.contains(i)) {
                return false;
            }
        }
        return true;

    }

    //knast variable
    public String goPrison() {
        currentField = 10;
        inJail = true;
        return JAIL_MESSAGE.formatted(name) + System.lineSeparator() + PAY_BAIL_MESSAGE.formatted(PayBailCommand.getCommandName());
    }

    public String buyHouse(String streetName) throws InputArgumentException, CommandException {
        Street street = getStreet(streetName);
        if (hasCompleteSet(street)) {
            return Bank.buyHouse(this, street);
        } else {
            throw new CommandException(COMPLETE_STEETSET_ERROR);
        }
    }

    private Street getStreet(String name) throws InputArgumentException {
        for (Street street : getAllStreets()) {
            if (street.getName().equals(name)) {
                return street;
            }
        }
        throw new InputArgumentException(ARGS_ERROR);
    }

    private List<Street> getAllStreets() {
        List<Street> streets = new ArrayList<>();
        for (BuyableField field : this.streets) {
            if (field.getClass() == Street.class) {
                streets.add((Street) field);
            }
        }
        return streets;
    }

    @Override
    public String toString() {
        return Outputs.playerToString(this);
    }

    public String payBail() throws CommandException {
        if (inJail) {
            return Bank.payBail(this);
        } else {
            throw new CommandException("you're not in jail!");
        }
    }
}
