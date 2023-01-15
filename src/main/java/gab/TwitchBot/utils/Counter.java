package gab.TwitchBot.Utils;

import gab.TwitchBot.Utils.Exceptions.CounterFormatException;

public class Counter {
    private String name;
    private int defaultValue;
    private int value;
    
    public Counter(String name, int defaultValue, int value) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public int getDefaultValue() {
        return defaultValue;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public void reset() {
        value = defaultValue;
    }
    public static Counter fromString(String counterString) throws CounterFormatException {
        String[] splitted = counterString.split(";");
        if(splitted.length != 4)
            throw new CounterFormatException();
        return new Counter(splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]));
    }

    @Override
    public String toString() {
        return "twitch;" + this.name + ";" + defaultValue + ";" + value;
    }
}
