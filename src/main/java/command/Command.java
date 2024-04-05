package main.java.command;


import main.java.structure.GameManager;
import main.java.structure.GamePhase;

public interface Command {

    CommandResult execute(GameManager manager, String[] commandArguments);

    int getNumberOfArguments();

    GamePhase getAllowedGamePhase();
}
