package main.java.command;

import main.java.exception.CommandException;
import main.java.exception.InputArgumentException;
import main.java.structure.GameManager;
import main.java.structure.GamePhase;

public class BuyHouseCommand implements Command {

    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.PLAYPHASE;
    private static final String COMMAND_NAME = "buy-house";
    private static final String COMMAND_SYNTAX = "<streetname>. Buy a house for this street.";
    private static final String WORD_SEPERATOR = "_";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        String streetName = commandArguments[0].replaceAll(WORD_SEPERATOR, " ");
        try {
            return new CommandResult(CommandResultType.SUCCESS, manager.getActivePlayer().buyHouse(streetName));
        } catch (InputArgumentException | CommandException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
    }

    @Override
    public int getNumberOfArguments() {
        return 1;
    }

    @Override
    public GamePhase getAllowedGamePhase() {
        return ALLOWED_GAME_PHASE;
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }

    public static String getCommandSyntax() {
        return COMMAND_SYNTAX;
    }
}
