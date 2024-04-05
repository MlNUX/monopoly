package main.java.command;


import main.java.structure.GameManager;
import main.java.structure.GamePhase;
import main.java.util.Outputs;

public final class HelpCommand implements Command {

    private static final GamePhase ALLOWED_GAME_PHASE = GamePhase.ALLWAYS;
    private static final String COMMAND_NAME = "help";
    private static final String COMMAND_SYNTAX = "Informations of the commands";

    @Override
    public CommandResult execute(GameManager manager, String[] commandArguments) {
        return new CommandResult(CommandResultType.SUCCESS, Outputs.getHelpMessage(manager.getCurrentGamePhase()));
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
