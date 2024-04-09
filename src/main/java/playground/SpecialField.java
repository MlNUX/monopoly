package playground;


import org.json.JSONObject;
import structure.Player;

public class SpecialField extends Field {

    private final FieldExecution fieldExecution;
    private final String parameter;

    public SpecialField(String name, int fieldIndex, FieldExecution fieldExecution, String parameter) {
        super(name, fieldIndex);
        this.fieldExecution = fieldExecution;
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    public static SpecialField deserialize(JSONObject obj) {
        String name = obj.getString("name");
        int fieldIndex = obj.getInt("fieldindex");
        FieldExecution fieldExecution = FieldExecution.valueOf(obj.getString("execution").toUpperCase());
        String parameter = obj.getString("parameter");
        return new SpecialField(name, fieldIndex, fieldExecution, parameter);
    }

    public String execute(Player currentPlayer) {
        return fieldExecution.execute(currentPlayer, parameter);
    }
}
