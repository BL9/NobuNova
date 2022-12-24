package gab.TwitchBot.Commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NameNotFoundException;

import gab.TwitchBot.Utils.CommandEvent;

public abstract class Command {
    protected String name;
    protected Set<String> aliases = new HashSet<String>();

    public boolean isThisCommand(String command) {
        return (name.compareTo(command) == 0) || aliases.contains(command);
    }

    public abstract void execute(CommandEvent event) throws IOException, NameNotFoundException, InterruptedException;

    protected void sendMessage(CommandEvent event, String message) {
        event.getInnerEvent().getChannel().sendMessage("/me " + message);
    }
}
