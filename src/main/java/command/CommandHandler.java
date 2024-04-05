
package main.java.command;



import main.java.structure.GameManager;
import main.java.structure.GamePhase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public final class CommandHandler {
    private static final String COMMAND_SEPARATOR_REGEX = " +";
    private static final String ERROR_PREFIX = "Error, ";
    private static final String COMMAND_NOT_FOUND_FORMAT = "command '%s' not found!";
    private static final String WRONG_ARGUMENTS_COUNT_FORMAT = "wrong number of arguments for command '%s'!";
    private static final String WRONG_GAME_PHASE_FORMAT = "it is the wrong gamephase for command '%s'!";
    private static final String INVALID_RESULT_TYPE_FORMAT = "Unexpected value: %s";

    private final GameManager manager;
    private final Map<String, Command> commands;
    private boolean running = false;

    public CommandHandler(GameManager manager) {
        this.manager = Objects.requireNonNull(manager);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                executeCommand(scanner.nextLine());
            }
        }
    }

    public void quit() {
        this.running = false;
    }

    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        executeCommand(commandName, commandArguments);
    }

    private void executeCommand(String commandName, String[] commandArguments) {
        if (!commands.containsKey(commandName)) {
            System.err.println(ERROR_PREFIX + COMMAND_NOT_FOUND_FORMAT.formatted(commandName));
        } else if (commands.get(commandName).getNumberOfArguments() != -1 && commands.get(commandName).getNumberOfArguments() != commandArguments.length) {
            System.err.println(ERROR_PREFIX + WRONG_ARGUMENTS_COUNT_FORMAT.formatted(commandName));
        } else if (commands.get(commandName).getAllowedGamePhase() != GamePhase.ALLWAYS && commands.get(commandName).getAllowedGamePhase() != manager.getCurrentGamePhase()) {
            System.err.println(ERROR_PREFIX + WRONG_GAME_PHASE_FORMAT.formatted(commandName));
        } else {
            CommandResult result = commands.get(commandName).execute(manager, commandArguments);
            String output = switch (result.getType()) {
                case SUCCESS -> result.getMessage();
                case FAILURE -> ERROR_PREFIX + result.getMessage();
            };
            if (output != null) {
                switch (result.getType()) {
                    case SUCCESS -> System.out.println(output);
                    case FAILURE -> System.err.println(output);
                    default -> throw new IllegalStateException(INVALID_RESULT_TYPE_FORMAT.formatted(result.getType()));
                }
            }
        }
    }

    private void initCommands() {
        this.addCommand(SetPlayerCommand.getCommandName(), new SetPlayerCommand());
        this.addCommand(HelpCommand.getCommandName(), new HelpCommand());
        this.addCommand(StartCapitalCommand.getCommandName(), new StartCapitalCommand());
        this.addCommand(StartGameCommand.getCommandName(), new StartGameCommand());
        this.addCommand(DiceCommand.getCommandName(), new DiceCommand());
        this.addCommand(BuyCommand.getCommandName(), new BuyCommand());
        this.addCommand(EndTurnCommand.getCommandName(), new EndTurnCommand());
        this.addCommand(ShowPlayerCommand.getCommandName(), new ShowPlayerCommand());
        this.addCommand(BuyHouseCommand.getCommandName(), new BuyHouseCommand());
    }

    private void addCommand(String commandName, Command command) {
        this.commands.put(commandName, command);
    }
}
