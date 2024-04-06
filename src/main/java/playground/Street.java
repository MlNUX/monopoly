package playground;


import structure.Player;

import java.awt.Color;

public class Street extends BuyableField {
    private final double housePrice;
    private final Color color;

    public Street(String name, int fieldIndex, double price, double rent, double housePrice, double multipier, double hypothek, Player owner, Color color) {
        super(name, fieldIndex, price, rent, hypothek, multipier, owner);
        this.housePrice = housePrice;
        this.color = color;
    }

    public double getHousePrice() {
        return housePrice;
    }
}
