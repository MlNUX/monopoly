package command;


import exception.CommandException;
import structure.GameManager;
import structure.GamePhase;

public class BuyCommand implements Command {
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.PLAYPHASE;
    private static final String COMMAND_NAME = "buy";
    private static final String COMMAND_SYNTAX = "You can buy the steet if you have enough money!";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        try {
            return new CommandResult(CommandResultType.SUCCESS, manager.getActivePlayer().buyStreet());
        } catch (CommandException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
    }

    @Override
    public int getNumberOfArguments() {
        return 0;
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
