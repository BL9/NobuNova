package gab;

import java.io.IOException;

import gab.DiscordBot.DiscordBot;
import gab.TwitchBot.TwitchBot;
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

        twitchBot.addCommand(new gab.TwitchBot.Commands.OptInAttendanceCheckCommand());
        twitchBot.addCommand(new gab.TwitchBot.Commands.OptOutAttendanceCheckCommand());
        twitchBot.addCommand(new gab.TwitchBot.Commands.GetAttendanceCommand());
        twitchBot.addCommand(new gab.TwitchBot.Commands.CounterCommand());

        twitchBot.addPrewareHandler(new AttendanceHandler(twitchBot));

        twitchBot.connect();
    }
    private static void setupDiscordBot(ConfigHelper config) throws IOException {
        discordBot = new DiscordBot(config);
        
        discordBot.addCommand(new gab.DiscordBot.Commands.GetAttendanceCommand());
        discordBot.addCommand(new gab.DiscordBot.Commands.LinkCommand());

        discordBot.updateCommands();
    }
}
