package main.com.specularity.printing.GCodes;

import javafx.util.Pair;

import java.util.regex.Pattern;

public class GCodeFactory {

    private static Pattern lineSeparator = Pattern.compile("[ \\t]+");

    public static GCode produceFromString(String line) {
        String[] tokens = lineSeparator.split(line);

        Pair<Character, Double> cmdPair = getParamValuePair(tokens[0]);
        if(cmdPair != null) {
            GCodeCommand cmd = new GCodeCommand(cmdPair.getKey(), cmdPair.getValue().intValue());
            for(int i=1; i<tokens.length; i++) {
                String token = tokens[i];
                Pair<Character, Double> parameter = getParamValuePair(token);
                if(parameter == null)
                    break;
                cmd.params.put(parameter.getKey(), parameter.getValue());
            }
            return cmd;
        }
        else
            return new GCodeComment(line);
    }

    private static Pair<Character, Double> getParamValuePair(String token) {
        if(!token.matches("[A-Z][0-9.]+"))
            return null;

        Character param = token.charAt(0);
        Double value = Double.valueOf(token.substring(1));

        return new Pair<>(param, value);
    }
}