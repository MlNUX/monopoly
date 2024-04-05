package main.java.command;


import main.java.exception.CommandException;
import main.java.structure.GameManager;
import main.java.structure.GamePhase;

public class EndTurnCommand implements Command {
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.PLAYPHASE;
    private static final String COMMAND_NAME = "end-turn";
    private static final String COMMAND_SYNTAX = "You can end your turn. Then it's the next player's turn.";
    private static final String SUCCESS_MESSAGE = "Now it's %s turn!";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        try {
            manager.getActivePlayer().endTurn();
            return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE.formatted(manager.getActivePlayer().getName()));
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
