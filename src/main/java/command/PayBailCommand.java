package command;

import exception.CommandException;
import structure.GameManager;
import structure.GamePhase;

public class PayBailCommand implements Command {
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.PLAYPHASE;
    private static final String COMMAND_NAME = "pay-bail";
    private static final String COMMAND_SYNTAX = "pay your bail if you're in jail";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        try {
            return new CommandResult(CommandResultType.SUCCESS, manager.payBail());
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
