package structure;

import exception.CommandException;
import exception.InputArgumentException;
import exception.InputFileException;
import playground.CommunityCard;
import playground.EventCard;
import playground.Field;
import util.FileInputManager;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class GameManager {
    private static final String DEFAULT_PATH = "C:\\Users\\lbgam\\Documents\\Uni\\Programmieren\\abschluss\\Monopoly\\src\\main\\resources\\config";
    private static final String INITIALIZING_PLAYER_ERROR = "Players not initialized!";
    private static final String EXIST_ERROR = "player doesn't exist!";
    private Player[] players;
    private double startCapital;
    private GamePhase currentGamePhase;
    private Field[] playGround;
    private CommunityCard[] communityCards;
    private EventCard[] eventCards;
    private List<List<Integer>> streetSets;
    private int activePlayer;

    public GameManager(double startCapital) {
        this.startCapital = startCapital;
        currentGamePhase = GamePhase.INITIALIZEPHASE;
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

    public void startGame() throws InputArgumentException, IOException, InputFileException {
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
}
