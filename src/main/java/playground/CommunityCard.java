package main.java.playground;


import main.java.structure.Player;

public class CommunityCard {

    private final String informationText;
    private final String additionalText;
    private final CardExexution exexution;
    private final String parameter;

    public CommunityCard(String informationText, String additionalText, CardExexution exexution, String parameter) {
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

    public void execute(Player currentPlayer) {
        exexution.execute(currentPlayer, parameter);
    }
}
