package gab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigHelper {
    /**
     *
     */
    private static final String CONFIG_FILE_NAME = "config.txt";
    Map<String, String> data;

    private boolean hasChanges = false;

    public ConfigHelper() throws IOException {
        data = new HashMap<String, String>();

        File file = new File(CONFIG_FILE_NAME);
        if(!file.exists())
            file.createNewFile();

        Scanner reader = new Scanner(file);
        while(reader.hasNextLine())
        {
            String line = reader.nextLine();
            String[] splitted = line.split("=");

            if(splitted.length > 1)
                data.put(splitted[0], splitted[1]);
        }
        reader.close();
    }
    public String getValue(String key, String message) {
        if(!data.containsKey(key))
        {
            System.out.println("No configuration found with key \"" + key + "\".\nPlease provide the value.\n" + message);
            System.out.print("> ");
            String value = System.console().readLine();

            addValue(key, value);
        }

        return data.get(key);
    }
    public String getValue(String key) {
        return getValue(key, "");
    }

    public void addValue(String key, String value) {
        data.put(key, value);
        hasChanges = true;
    }
    public boolean hasKey(String key) {
        return data.containsKey(key);
    }

    public void save() throws IOException {
        if(hasChanges)
        {
            FileWriter writer = new FileWriter(CONFIG_FILE_NAME, false);
        
            for(String key : data.keySet()) {
                writer.write(key + "=" + data.get(key) + "\n");
            }
    
            writer.close();
        }
    }
}
