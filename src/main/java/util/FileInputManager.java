package util;

import exception.InputFileException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import playground.CardExexution;
import playground.CommunityCard;
import playground.EventCard;
import playground.Field;
import playground.FieldExecution;
import playground.SpecialField;
import playground.Street;
import playground.Trainstation;
import structure.GameManager;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileInputManager {

    private static final String FILE_ARG_LENGTH_ERROR = "argumentlength incorrect in file '%s'!";
    private static final String FILE_ARG_TYPE_ERROR = "wrong argument types in file '%s'!";
    private static final String FILE_MULTIPLE_INDEX_ERROR = "multiple field-declarations are not allowed!";
    private static final String FILE_COMPLETE_ERROR = "all fields have to be initialized!";
    private static final String STREETCONFIG_PATH = "\\streetconfig.txt";
    private static final String TRAINSTATIONCONFIG_PATH = "\\trainstationconfig.txt";
    private static final String SPECIALCONFIG_PATH = "\\specialfeldconfig.txt";
    private static final String COMMUNITYCARD_PATH = "\\communityconfig.txt";
    private static final String EVENTCARD_PATH = "\\eventconfig.txt";
    private static final String STREETSETCONFIG_PATH = "\\streetsetconfig.txt";
    private static final int PLAYGROUND_SIZE = Integer.parseInt(GameManager.getProperties("gameGroundSize"));
    private static final String COMMAND_PREFIX = "//";
    private static final String SPLIT_REGEX = ",";


    private FileInputManager() {
    }
/*
    public static Field[] readoutFieldFile(String path) throws InputFileException, ParseException {
        Field[] fields = new Field[PLAYGROUND_SIZE];
        JSONObject obj = new JSONObject(path + "\\streets.json");

        JSONArray streets = obj.getJSONArray("streets");
        for (int i = 0; i < streets.length(); i++) {
            Street street = Street.deserialize(streets.getJSONObject(i));
            fields[street.getFieldIndex()] = street;
        }
        JSONArray stations = obj.getJSONArray("stations");
        for (int i = 0; i < stations.length(); i++) {
            Trainstation station = Trainstation.deserialize(stations.getJSONObject(i));
            fields[station.getFieldIndex()] = station;
        }
        JSONArray specials = obj.getJSONArray("specialfield");
        for (int i = 0; i < specials.length(); i++) {
            SpecialField field = SpecialField.deserialize(specials.getJSONObject(i));
            fields[field.getFieldIndex()] = field;
        }

        for (Field field : fields) {
            if (field == null) {
                throw new InputFileException(FILE_COMPLETE_ERROR);
            }
        }

        return fields;
    }
*/

    public static Field[] readoutFieldFile(String path) throws InputFileException, IOException {
        Field[] playField = new Field[PLAYGROUND_SIZE];
        BufferedReader streetReader = new BufferedReader(new FileReader(path + STREETCONFIG_PATH));
        String nextLine = streetReader.readLine();
        while (nextLine != null) {
            if (!nextLine.startsWith(COMMAND_PREFIX)) {
                parseStreetLine(playField, nextLine);
            }
            nextLine = streetReader.readLine();
        }
        streetReader.close();

        BufferedReader trainReader = new BufferedReader(new FileReader(path + TRAINSTATIONCONFIG_PATH));
        nextLine = trainReader.readLine();
        while (nextLine != null) {
            if (!nextLine.startsWith(COMMAND_PREFIX)) {
                parseStationLine(playField, nextLine);
            }
            nextLine = trainReader.readLine();
        }
        trainReader.close();

        BufferedReader specialReader = new BufferedReader(new FileReader(path + SPECIALCONFIG_PATH));
        nextLine = specialReader.readLine();
        while (nextLine != null) {
            if (!nextLine.startsWith(COMMAND_PREFIX)) {
                parseSpecialField(playField, nextLine);
            }
            nextLine = specialReader.readLine();
        }
        specialReader.close();

        for (Field field : playField) {
            if (field == null) {
                throw new InputFileException(FILE_COMPLETE_ERROR);
            }
        }

        return playField;
    }

    private static void parseStreetLine(Field[] playField, String line) throws InputFileException {
        String[] lineSegments = line.split(SPLIT_REGEX);
        if (lineSegments.length != 10) {
            throw new InputFileException(FILE_ARG_LENGTH_ERROR.formatted(STREETCONFIG_PATH));
        }
        try {
            String name = lineSegments[0];
            double price = Double.parseDouble(lineSegments[1]);
            double rent = Double.parseDouble(lineSegments[2]);
            double housePrice = Double.parseDouble(lineSegments[3]);
            double multiplier = Double.parseDouble(lineSegments[4]);
            double hypothek = Double.parseDouble(lineSegments[5]);
            Color fieldColor = new Color(Integer.parseInt(lineSegments[7]),
                    Integer.parseInt(lineSegments[8]),
                    Integer.parseInt(lineSegments[9]));
            int fieldIndex = Integer.parseInt(lineSegments[6]);
            if (playField[fieldIndex] == null) {
                playField[fieldIndex] = new Street(name, fieldIndex, price, rent, housePrice, multiplier, hypothek, null, fieldColor);
            } else {
                throw new InputFileException(FILE_MULTIPLE_INDEX_ERROR);
            }
        } catch (NumberFormatException e) {
            throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(STREETCONFIG_PATH));
        }
    }

    private static void parseStationLine(Field[] playField, String line) throws InputFileException {
        String[] lineSegments = line.split(SPLIT_REGEX);
        if (lineSegments.length != 6) {
            throw new InputFileException(FILE_ARG_LENGTH_ERROR.formatted(TRAINSTATIONCONFIG_PATH));
        }
        try {
            String name = lineSegments[0];
            double price = Double.parseDouble(lineSegments[1]);
            double rent = Double.parseDouble(lineSegments[2]);
            double multiplier = Double.parseDouble(lineSegments[3]);
            double hypothek = Double.parseDouble(lineSegments[4]);
            int fieldIndex = Integer.parseInt(lineSegments[5]);
            if (playField[fieldIndex] == null) {
                playField[fieldIndex] = new Trainstation(name, fieldIndex, price, rent, hypothek, multiplier, null);
            } else {
                throw new InputFileException(FILE_MULTIPLE_INDEX_ERROR);
            }
        } catch (NumberFormatException e) {
            throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(TRAINSTATIONCONFIG_PATH));
        }
    }

    private static void parseSpecialField(Field[] playField, String line) throws InputFileException {
        String[] lineSegments = line.split(SPLIT_REGEX);
        if (lineSegments.length != 4) {
            throw new InputFileException(FILE_ARG_LENGTH_ERROR.formatted(SPECIALCONFIG_PATH));
        }
        try {
            String name = lineSegments[0];
            int fieldIndex = Integer.parseInt(lineSegments[1]);
            FieldExecution fieldExecution = FieldExecution.valueOf(lineSegments[2].toUpperCase());
            String parameter = lineSegments[3];
            if (playField[fieldIndex] == null) {
                playField[fieldIndex] = new SpecialField(name, fieldIndex, fieldExecution, parameter);
            } else {
                throw new InputFileException(FILE_MULTIPLE_INDEX_ERROR);
            }

        } catch (NumberFormatException e) {
            throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(SPECIALCONFIG_PATH));
        }
    }

    public static CommunityCard[] readoutCommunityFile(String path) throws IOException, InputFileException {
        List<CommunityCard> communityCards = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path + COMMUNITYCARD_PATH));
        String nextLine = reader.readLine();
        while (nextLine != null) {
            if (!nextLine.startsWith(COMMAND_PREFIX)) {
                parseCommunityCard(communityCards, nextLine);
            }
            nextLine = reader.readLine();
        }
        reader.close();

        CommunityCard[] cards = new CommunityCard[communityCards.size()];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = communityCards.get(i);
        }
        return cards;
    }

    private static void parseCommunityCard(List<CommunityCard> communityCards, String line) throws InputFileException {
        String[] lineSegments = line.split(SPLIT_REGEX);
        if (lineSegments.length != 4) {
            throw new InputFileException(FILE_ARG_LENGTH_ERROR.formatted(COMMUNITYCARD_PATH));
        }
        try {
            String informationText = lineSegments[0];
            String additionalText = lineSegments[1];
            CardExexution exexution = CardExexution.valueOf(lineSegments[2].toUpperCase());
            String parameter = lineSegments[3];
            communityCards.add(new CommunityCard(informationText, additionalText, exexution, parameter));
        } catch (NumberFormatException e) {
            throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(COMMUNITYCARD_PATH));
        }
    }

    public static EventCard[] readoutEventFile(String path) throws IOException, InputFileException {
        List<EventCard> eventCards = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path + EVENTCARD_PATH));
        String nextLine = reader.readLine();
        while (nextLine != null) {
            if (!nextLine.startsWith(COMMAND_PREFIX)) {
                parseEventCard(eventCards, nextLine);
            }
            nextLine = reader.readLine();
        }
        reader.close();

        EventCard[] cards = new EventCard[eventCards.size()];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = eventCards.get(i);
        }
        return cards;
    }

    private static void parseEventCard(List<EventCard> eventCards, String line) throws InputFileException {
        String[] lineSegments = line.split(SPLIT_REGEX);
        if (lineSegments.length != 4) {
            throw new InputFileException(FILE_ARG_LENGTH_ERROR.formatted(EVENTCARD_PATH));
        }
        try {
            String informationText = lineSegments[0];
            String additionalText = lineSegments[1];
            CardExexution exexution = CardExexution.valueOf(lineSegments[2].toUpperCase());
            String parameter = lineSegments[3];
            eventCards.add(new EventCard(informationText, additionalText, exexution, parameter));
        } catch (NumberFormatException e) {
            throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(EVENTCARD_PATH));
        }
    }

    public static List<List<Integer>> readoutStreetSets(String path) throws IOException, InputFileException {
        BufferedReader reader = new BufferedReader(new FileReader(path + STREETSETCONFIG_PATH));
        String nextLine = reader.readLine();
        List<List<Integer>> tempStreetSets = new ArrayList<>();
        while (nextLine != null) {
            if (!nextLine.startsWith(COMMAND_PREFIX)) {
                tempStreetSets.add(parseStreetSet(nextLine));
            }
            nextLine = reader.readLine();
        }
        reader.close();
        return tempStreetSets;
    }

    private static List<Integer> parseStreetSet(String line) throws InputFileException {
        String[] lineSegments = line.split(SPLIT_REGEX);
        List<Integer> streetSet = new ArrayList<>();
        for (String segment : lineSegments) {
            try {
                int streetIndex = Integer.parseInt(segment);
                if (!streetSet.contains(streetIndex)) {
                    streetSet.add(streetIndex);
                } else {
                    throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(STREETSETCONFIG_PATH));
                }
            } catch (NumberFormatException e) {
                throw new InputFileException(FILE_ARG_TYPE_ERROR.formatted(STREETSETCONFIG_PATH));
            }
        }
        return streetSet;
    }
}
