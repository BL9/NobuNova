package gab;

import java.io.IOException;

import gab.TwitchBot.TwitchBot;

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

            twitchBot.connect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
