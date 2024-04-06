package command;

import structure.GameManager;
import structure.GamePhase;

public final class StartCapitalCommand implements Command {

    private static final String COMMAND_NAME = "set-startCapital";
    private static final String COMMAND_SYNTAX = "<value>. The Cash for the Gamestart";
    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.INITIALIZEPHASE;
    private static final String VALUE_FORMAT_ERROR = "value has to be a number!";
    private static final String SUCCESS_MESSAGE = "Startcapital is now %s !";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        double cash;
        try {
            cash = Double.parseDouble(commandArguments[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResultType.FAILURE, VALUE_FORMAT_ERROR);
        }

        manager.setStartCapital(cash);
        return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE.formatted(String.format("%.2f", cash)));
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }

    public static String getCommandSyntax() {
        return COMMAND_SYNTAX;
    }

    @Override
    public int getNumberOfArguments() {
        return 1;
    }

    @Override
    public GamePhase getAllowedGamePhase() {
        return ALLOWED_GAME_PHASE;
    }
}
