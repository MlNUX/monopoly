package command;

public class CommandResult {

    private final String message;
    private final CommandResultType type;

    public CommandResult(CommandResultType type, String resultMessage) {
        this.message = resultMessage;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public CommandResultType getType() {
        return type;
    }
}
