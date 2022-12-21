package gab;

import java.io.IOException;

import gab.TwitchBot.TwitchBot;
import gab.TwitchBot.Commands.OptInAttendanceCheckCommand;
import gab.Utils.ConfigHelper;

/**
 * Hello world!
 */
public final class App {
    static ConfigHelper config;
    static TwitchBot twitchBot;

    private App() {
        
    }


    public static void main(String[] args) {
        try {
            config = new ConfigHelper();
            twitchBot = new TwitchBot(config);
            twitchBot.addCommand(new OptInAttendanceCheckCommand(twitchBot));

            twitchBot.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
