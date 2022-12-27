package gab.DiscordBot.Commands;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NameNotFoundException;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class Command {
    protected String name;
    protected String description;
    protected Set<OptionData> options = new HashSet<OptionData>();

    public boolean isThisCommand(String command) {
        return (name.compareTo(command) == 0);
    }    

    public abstract void execute(SlashCommandInteractionEvent event) throws IOException, NameNotFoundException, InterruptedException;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Collection<OptionData> getOptions() {
        return options;
    }
}
