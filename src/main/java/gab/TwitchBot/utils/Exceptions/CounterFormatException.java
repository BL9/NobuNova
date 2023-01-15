package gab.TwitchBot.Utils.Exceptions;

public class CounterFormatException extends Exception{

    public CounterFormatException() {
        super("Invalid counter string format");
    }
}
