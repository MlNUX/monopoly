package main.java.command;

import main.java.structure.GameManager;
import main.java.structure.GamePhase;

public final class SetPlayerCommand implements Command {
    private static final String ARGUMENT_LENGTH_ERROR = "wrong argumentlength!";
    private static final String ARGUMENT_DUPLICATE_ERROR = "every name can taken once!";
    private static final String SUCCESS_MESSAGE = "Player are initialized!";
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.INITIALIZEPHASE;
    private static final String COMMAND_SYNTAX = "<name> <symbol>. You need minimum two players!";
    private static final String COMMAND_NAME = "set-players";

    @Override
    public CommandResult execute(GameManager manager, String[] comArgs) {
        if (comArgs.length < 4 || comArgs.length % 2 == 1) {
            return new CommandResult(CommandResultType.FAILURE, ARGUMENT_LENGTH_ERROR);
        }

        for (int i = 0; i < comArgs.length; i++) {
            for (int j = i + 1; j < comArgs.length; j++) {
                if (i != j && comArgs[i].equals(comArgs[j])) {
                    return new CommandResult(CommandResultType.FAILURE, ARGUMENT_DUPLICATE_ERROR);
                }
            }
        }
        manager.setPlayers(comArgs);
        return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE);

    }

    public static String getCommandSyntax(){
        return COMMAND_SYNTAX;
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public int getNumberOfArguments() {
        return -1;
    }

    @Override
    public GamePhase getAllowedGamePhase() {
        return ALLOWED_GAME_PHASE;
    }
}
