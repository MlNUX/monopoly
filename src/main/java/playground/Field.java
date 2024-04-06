package playground;

public abstract class Field {

    private final String name;
    private final int fieldIndex;

    protected Field(String name, int fieldIndex){
        this.name = name;
        this.fieldIndex = fieldIndex;
    }

    public String getName() {
        return name;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }
}
