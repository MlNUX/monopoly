package command;


import structure.GameManager;
import structure.GamePhase;

public interface Command {

    CommandResult execute(GameManager manager, String[] commandArguments);

    int getNumberOfArguments();

    GamePhase getAllowedGamePhase();
}
