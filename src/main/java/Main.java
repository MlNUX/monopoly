import main.java.command.CommandHandler;
import main.java.structure.GameManager;
import main.java.util.Outputs;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Outputs.sendMessage("Wellcome to Monopoly!");
        GameManager manager = new GameManager(20000);
        CommandHandler handler = new CommandHandler(manager);
        handler.handleUserInput();
    }
}