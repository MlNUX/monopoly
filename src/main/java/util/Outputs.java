package main.java.util;


import main.java.command.BuyCommand;
import main.java.command.BuyHouseCommand;
import main.java.command.DiceCommand;
import main.java.command.EndTurnCommand;
import main.java.command.HelpCommand;
import main.java.command.SetPlayerCommand;
import main.java.command.ShowPlayerCommand;
import main.java.command.StartCapitalCommand;
import main.java.command.StartGameCommand;
import main.java.playground.BuyableField;
import main.java.structure.GamePhase;
import main.java.structure.Player;

public final class Outputs {

    private static final String HELP_SEPERATOR_STRING = " : ";
    private static final String MONEY_FORMAT = "%.2f";
    private static final String PLAYER_OUTPUT_SEPERATOR = "-------------------------------";
    private static final String MONEY_PREFIX = "Money : ";
    private static final String NAME_PREFIX = "Name  : ";
    private static final String CURRENTFIELD_PREFIX = "Currentfield :  '%s'";
    private static final String OWNED_STREETS_PREFIX = "Owned Streets : ";
    private static final String STREET_FORMAT = "'%s'";
    private static final String LAYOUT_FILE = "\\playGround.txt";

    private Outputs() {
    }

    public static String getHelpMessage(GamePhase currentGamePhase) {
        if (currentGamePhase == GamePhase.INITIALIZEPHASE) {
            return HelpCommand.getCommandName() + HELP_SEPERATOR_STRING + HelpCommand.getCommandSyntax() + System.lineSeparator()
                    + SetPlayerCommand.getCommandName() + HELP_SEPERATOR_STRING + SetPlayerCommand.getCommandSyntax() + System.lineSeparator()
                    + StartCapitalCommand.getCommandName() + HELP_SEPERATOR_STRING + StartCapitalCommand.getCommandSyntax() + System.lineSeparator()
                    + StartGameCommand.getCommandName() + HELP_SEPERATOR_STRING + StartGameCommand.getCommandSyntax();
        } else {
            return HelpCommand.getCommandName() + HELP_SEPERATOR_STRING + HelpCommand.getCommandSyntax() + System.lineSeparator()
                    + DiceCommand.getCommandName() + HELP_SEPERATOR_STRING + DiceCommand.getCommandSyntax() + System.lineSeparator()
                    + BuyCommand.getCommandName() + HELP_SEPERATOR_STRING + BuyCommand.getCommandSyntax() + System.lineSeparator()
                    + EndTurnCommand.getCommandName() + HELP_SEPERATOR_STRING + EndTurnCommand.getCommandSyntax() + System.lineSeparator()
                    + ShowPlayerCommand.getCommandName() + HELP_SEPERATOR_STRING + ShowPlayerCommand.getCommandSyntax() + System.lineSeparator()
                    + BuyHouseCommand.getCommandName() + HELP_SEPERATOR_STRING + BuyHouseCommand.getCommandSyntax();
        }
    }

    public static void sendMessage(String message) {
        System.out.println(message);
    }

    public static String playerToString(Player player) {
        StringBuilder builder = new StringBuilder();
        builder.append(NAME_PREFIX).append(player.getName()).append(System.lineSeparator());
        builder.append(MONEY_PREFIX).append(String.format(MONEY_FORMAT, player.getMoney())).append(System.lineSeparator());
        builder.append(PLAYER_OUTPUT_SEPERATOR).append(System.lineSeparator());
        builder.append(CURRENTFIELD_PREFIX.formatted(player.getCurrentField().getName())).append(System.lineSeparator());
        if (!player.getStreets().isEmpty()) {
            builder.append(OWNED_STREETS_PREFIX);
        } else {
            return builder.deleteCharAt(builder.length() - 1).toString();
        }
        for (BuyableField field : player.getStreets()) {
            builder.append(STREET_FORMAT.formatted(field.getName())).append(System.lineSeparator());
            builder.append("\t\t\t\t");
        }
        return builder.delete(builder.length() - 6, builder.length() - 1).toString();
    }

}
