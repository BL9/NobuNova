package gab;

import java.io.IOException;

import gab.DiscordBot.DiscordBot;
import gab.TwitchBot.TwitchBot;
import gab.TwitchBot.Commands.GetAttendanceCommand;
import gab.TwitchBot.Commands.OptInAttendanceCheckCommand;
import gab.TwitchBot.Commands.OptOutAttendanceCheckCommand;
import gab.TwitchBot.Handlers.AttendanceHandler;
import gab.Utils.ConfigHelper;
import gab.Utils.TwitchHelper;

public final class App {
    static ConfigHelper config;
    static TwitchBot twitchBot;
    static DiscordBot discordBot;

    private App() {
        
    }


    public static void main(String[] args) {
        try {
            config = new ConfigHelper();
            setupTwitchBot(config);
            setupDiscordBot(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupTwitchBot(ConfigHelper config) throws IOException {
        TwitchHelper.getInstance(config);
        twitchBot = new TwitchBot(config);

        twitchBot.addCommand(new OptInAttendanceCheckCommand());
        twitchBot.addCommand(new OptOutAttendanceCheckCommand());
        twitchBot.addCommand(new GetAttendanceCommand());

        twitchBot.addPrewareHandler(new AttendanceHandler(twitchBot));

        twitchBot.connect();
    }
    private static void setupDiscordBot(ConfigHelper config) throws IOException {
        discordBot = new DiscordBot(config);
    }
}
