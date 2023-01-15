package gab.TwitchBot.Utils;

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
}
