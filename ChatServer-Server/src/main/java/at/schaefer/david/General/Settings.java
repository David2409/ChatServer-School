package at.schaefer.david.General;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    public String database;
    public String databaseUser;
    public String databasePassword;
    public char[] invalidCharacters;

    public static Settings GetSettings() throws IOException {
        Settings settings = new Settings();
        String[][] lines = GetLines("settings");
        for(String[] line : lines){
            if ("database".equals(line[0])) {
                settings.database = line[1];
            } else if ("databaseUser".equals(line[0])) {
                settings.databaseUser = line[1];
            } else if ("databasePassword".equals(line[0])) {
                settings.databasePassword = line[1];
            } else if ("invalidCharacters".equals(line[0])) {
                settings.invalidCharacters = line[1].toCharArray();
            }
        }
        return settings;
    }

    private static String[][] GetLines(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String[]> lines = new ArrayList<String[]>();
        String newLine;
        while((newLine = reader.readLine()) != null){
            if(newLine != ""){
                String[] line = Split(newLine, '=');
                if(line[0].length() != 0 && line[0].charAt(0) != '#'){
                    lines.add(line);
                }
            }
        }
        return ToArray(lines);
    }

    private static String[] Split(String input, char character){
        int by = 0;
        String[] erg = new String[]{"", ""};
        while(input.length() > by){
            if(input.charAt(by) != character){
                erg[0] += input.charAt(by);
            }
            else {
                break;
            }
            by++;
        }
        by++;
        while(input.length() > by){
            erg[1] += input.charAt(by);
            by++;
        }
        return erg;
    }

    private static String[][] ToArray(List<String[]> arrays){
        String[][] erg = new String[arrays.size()][];
        for(int i = 0; i < arrays.size(); i++){
            erg[i] = arrays.get(i);
        }
        return erg;
    }
}