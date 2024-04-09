package playground;


import org.json.JSONObject;
import structure.Player;

public class Trainstation extends BuyableField {
    public Trainstation(String name, int fieldIndex, double price, double rent, double multiplier, double mortgage, Player owner) {
        super(name, fieldIndex, price, rent, mortgage, multiplier, owner);
    }

    public static Trainstation deserialize(JSONObject obj) {
        String name = obj.getString("name");
        double price = obj.getDouble("price");
        double rent = obj.getDouble("rent");
        double multiplier = obj.getDouble("multiplier");
        double mortgage = obj.getDouble("mortgage");
        int fieldIndex = obj.getInt("fieldindex");
        return new Trainstation(name, fieldIndex, price, rent, multiplier, mortgage, null);
    }
}
