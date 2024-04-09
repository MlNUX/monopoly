package structure;

import exception.CommandException;
import exception.InputArgumentException;
import exception.InputFileException;
import org.json.simple.parser.ParseException;
import playground.CommunityCard;
import playground.EventCard;
import playground.Field;
import util.FileInputManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.random.RandomGenerator;

public class GameManager {
    private static Properties gameProperties;
    private static final String DEFAULT_PATH = "src\\main\\resources\\config";
    private static final String INITIALIZING_PLAYER_ERROR = "Players not initialized!";
    private static final String EXIST_ERROR = "player doesn't exist!";
    private static final String SEPERATOR_SYMBOL = ":";
    private static final String INTEGER_LENGTH_FORMAT = "%2d";
    private Player[] players;
    private double startCapital;
    private GamePhase currentGamePhase;
    private Field[] playGround;
    private CommunityCard[] communityCards;
    private EventCard[] eventCards;
    private List<List<Integer>> streetSets;
    private int activePlayer;

    public GameManager() {
        currentGamePhase = GamePhase.INITIALIZEPHASE;
        gameProperties = new Properties();
        try {
            gameProperties.load(new FileInputStream(DEFAULT_PATH + "\\game.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.startCapital = Double.parseDouble(gameProperties.getProperty("startCapital"));
    }

    public void setPlayers(String[] playerString) {
        this.players = new Player[playerString.length / 2];
        int playersIndex = 0;
        for (int i = 0; i < playerString.length; i += 2) {
            this.players[playersIndex] = new Player(playerString[i], playerString[i + 1], startCapital, this);
            playersIndex++;
        }
    }

    public void setStartCapital(double startCapital) {
        this.startCapital = startCapital;
        if (players[0] != null) {
            for (Player p : players) {
                p.setCapital(startCapital);
            }
        }
    }

    public List<List<Integer>> getStreetSets() {
        return streetSets;
    }

    public Field[] getPlayGround() {
        return playGround.clone();
    }

    public GamePhase getCurrentGamePhase() {
        return currentGamePhase;
    }

    public void startGame() throws InputArgumentException, IOException, InputFileException, ParseException {
        if (players == null) {
            throw new InputArgumentException(INITIALIZING_PLAYER_ERROR);
        }
        playGround = FileInputManager.readoutFieldFile(DEFAULT_PATH);
        communityCards = FileInputManager.readoutCommunityFile(DEFAULT_PATH);
        eventCards = FileInputManager.readoutEventFile(DEFAULT_PATH);
        streetSets = FileInputManager.readoutStreetSets(DEFAULT_PATH);
        currentGamePhase = GamePhase.PLAYPHASE;
        activePlayer = 0;
    }

    public String dice() throws CommandException {
        return players[activePlayer].makeNextMove();
    }

    public Player getActivePlayer() {
        return players[activePlayer];
    }

    public void changeActivePlayer() {
        int indexOfNextPlayer = Math.floorMod(activePlayer + 1, players.length);
        if (!players[indexOfNextPlayer].hasGivenUp()) {
            activePlayer = indexOfNextPlayer;
        } else {
            changeActivePlayer();
        }
    }

    public Player getPlayer(String name) throws InputArgumentException {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        throw new InputArgumentException(EXIST_ERROR);
    }

    public String chooseEventCard(Player player) {
        RandomGenerator random = new Random();
        EventCard card = eventCards[random.nextInt(eventCards.length)];
        return card.getInformationText() + (((card.getAdditionalText().isEmpty()) ? "" : System.lineSeparator() + card.getAdditionalText())
                + System.lineSeparator() + card.execute(player));
    }

    public String chooseCommunityCard(Player player) {
        RandomGenerator random = new Random();
        CommunityCard card = communityCards[random.nextInt(communityCards.length)];
        card.execute(player);
        return card.getInformationText() + ((card.getAdditionalText().isEmpty()) ? "" : System.lineSeparator() + card.getAdditionalText());
    }

    public String getNextFields() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            Field field = playGround[getActivePlayer().getCurrentField().getFieldIndex() + i + 1];
            builder.append(String.format(INTEGER_LENGTH_FORMAT, field.getFieldIndex()))
                    .append(SEPERATOR_SYMBOL)
                    .append(field.getName())
                    .append(System.lineSeparator());
        }
        return builder.substring(0, builder.length() - 1);
    }

    public String payBail() throws CommandException {
        return getActivePlayer().payBail();
    }

    public static String getProperties(String prop) {
        return gameProperties.getProperty(prop);
    }
}
