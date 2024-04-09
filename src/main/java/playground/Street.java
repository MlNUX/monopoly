package playground;


import org.json.JSONObject;
import structure.Player;

import java.awt.Color;

public class Street extends BuyableField {
    private final double housePrice;
    private final Color color;

    public Street(String name, int fieldIndex, double price, double rent, double housePrice, double multiplier, double mortgage, Player owner, Color color) {
        super(name, fieldIndex, price, rent, mortgage, multiplier, owner);
        this.housePrice = housePrice;
        this.color = color;
    }

    public static Street deserialize(JSONObject obj) {
        String name = obj.getString("name");
        double price = obj.getDouble("price");
        double rent = obj.getDouble("rent");
        double housePrice = obj.getDouble("housePrice");
        double multiplier = obj.getDouble("multiplier");
        double mortgage = obj.getDouble("mortgage");
        int fieldIndex = obj.getInt("fieldindex");
        Color color = new Color(obj.getInt("r"), obj.getInt("g"), obj.getInt("b"));
        return new Street(name, fieldIndex, price, rent, housePrice, multiplier, mortgage, null, color);
    }

    public double getHousePrice() {
        return housePrice;
    }
}
