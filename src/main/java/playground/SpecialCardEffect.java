package playground;


import structure.Player;

@FunctionalInterface
public interface SpecialCardEffect {

    String execute(Player executor, String parameter);

}
