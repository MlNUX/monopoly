package command;

import exception.InputArgumentException;
import exception.InputFileException;
import structure.GameManager;
import structure.GamePhase;

import java.io.IOException;

public final class StartGameCommand implements Command {
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.INITIALIZEPHASE;
    private static final String COMMAND_NAME = "start-game";
    private static final String COMMAND_SYNTAX = "Start the game";
    private static final String SUCCESS_MESSAGE = "Game started!";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        try {
            manager.startGame();
            return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE);
        } catch (InputArgumentException | IOException | InputFileException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }


    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }

    public static String getCommandSyntax() {
        return COMMAND_SYNTAX;
    }

    @Override
    public int getNumberOfArguments() {
        return 0;
    }

    @Override
    public GamePhase getAllowedGamePhase() {
        return ALLOWED_GAME_PHASE;
    }
}
