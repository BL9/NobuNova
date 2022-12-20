package gab.TwitchBot.Commands;

import java.util.Set;

import gab.TwitchBot.utils.CommandEvent;

public abstract class Command {
    private String name;
    private Set<String> aliases;

    public Command(String name, Set<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public boolean isThisCommand(String command) {
        return (name == command) || aliases.contains(command);
    }

    public abstract void execute(CommandEvent event);
}
