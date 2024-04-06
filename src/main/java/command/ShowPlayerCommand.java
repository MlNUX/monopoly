package command;

import exception.InputArgumentException;
import structure.GameManager;
import structure.GamePhase;

public class ShowPlayerCommand implements Command {

    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.PLAYPHASE;

    private static final String COMMAND_NAME = "show-player";
    private static final String COMMAND_SYNTAX = "<playerName>. Get informations about this player";


    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        try {
            return new CommandResult(CommandResultType.SUCCESS, manager.getPlayer(commandArguments[0]).toString());
        } catch (InputArgumentException e) {
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
