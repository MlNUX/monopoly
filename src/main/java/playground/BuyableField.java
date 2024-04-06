package playground;


import structure.Player;

public abstract class BuyableField extends Field {

    private final double price;
    private final double rent;
    private final double hypothek;
    private final double multiplier;
    private Player owner;

    protected BuyableField(String name, int fieldIndex, double price, double rent, double hypothek, double multiplier, Player owner) {
        super(name, fieldIndex);
        this.price = price;
        this.rent = rent;
        this.hypothek = hypothek;
        this.multiplier = multiplier;
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }

    public double getRent() {
        return rent;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
