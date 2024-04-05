package main.java.playground;


import main.java.structure.Player;

public class Trainstation extends BuyableField {
    public Trainstation(String name, int fieldIndex, double price, double rent, double hypothek, double multiplier, Player owner) {
        super(name, fieldIndex, price, rent, hypothek, multiplier, owner);
    }
}
