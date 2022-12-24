package gab.TwitchBot.Handlers;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import gab.TwitchBot.TwitchBot;
import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Logger;

public abstract class WareHandler {
    protected final Logger logger;

    public WareHandler(TwitchBot bot) {
        this.logger = bot.logger;
    }

    public abstract void execute(CommandEvent event) throws IOException, InterruptedException, InterruptedException, NameNotFoundException;
}
