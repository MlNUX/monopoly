package playground;


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

    public String execute(Player currentPlayer) {
        return fieldExecution.execute(currentPlayer, parameter);
    }
}
