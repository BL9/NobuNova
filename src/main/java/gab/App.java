package gab;

import java.io.IOException;

import gab.TwitchBot.TwitchBot;
import gab.TwitchBot.Commands.GetAttendanceCommand;
import gab.TwitchBot.Commands.OptInAttendanceCheckCommand;
import gab.TwitchBot.Commands.OptOutAttendanceCheckCommand;
import gab.TwitchBot.Handlers.AttendanceHandler;
import gab.Utils.ConfigHelper;
import gab.Utils.TwitchHelper;

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
            TwitchHelper.getInstance(config);
            twitchBot = new TwitchBot(config);

            twitchBot.addCommand(new OptInAttendanceCheckCommand());
            twitchBot.addCommand(new OptOutAttendanceCheckCommand());

            twitchBot.addPrewareHandler(new AttendanceHandler(twitchBot));

            twitchBot.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
