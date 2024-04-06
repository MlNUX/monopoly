import command.CommandHandler;
import structure.GameManager;
import util.Outputs;

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