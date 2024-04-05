package main.java.playground;


import main.java.structure.Player;

public class EventCard {

    private final String informationText;
    private final String additionalText;
    private final CardExexution exexution;
    private final String parameter;

    public EventCard(String informationText, String additionalText, CardExexution exexution, String parameter) {
        this.informationText = informationText;
        this.additionalText = additionalText;
        this.exexution = exexution;
        this.parameter = parameter;
    }

    public String getInformationText() {
        return informationText;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public String execute(Player currentPlayer) {
        return exexution.execute(currentPlayer, parameter);
    }
}
