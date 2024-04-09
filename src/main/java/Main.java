import command.CommandHandler;
import structure.GameManager;
import util.Outputs;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Outputs.sendMessage("Welcome to Monopoly!");
        GameManager manager = new GameManager();
        CommandHandler handler = new CommandHandler(manager);
        handler.handleUserInput();
    }
}