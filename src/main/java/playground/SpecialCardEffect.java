package main.java.playground;


import main.java.structure.Player;

@FunctionalInterface
public interface SpecialCardEffect {

    String execute(Player executor, String parameter);

}
