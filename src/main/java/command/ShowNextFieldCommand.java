package command;

import structure.GameManager;
import structure.GamePhase;

public class ShowNextFieldCommand implements Command {
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.PLAYPHASE;
    private static final String COMMAND_NAME = "show-next";
    private static final String COMMAND_SYNTAX = "show the next 12 fields of the active player";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        return new CommandResult(CommandResultType.SUCCESS,manager.getNextFields());
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
