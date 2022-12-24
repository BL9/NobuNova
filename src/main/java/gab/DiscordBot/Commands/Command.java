package gab.DiscordBot.Commands;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class Command {
    protected String name;
    protected String description;
    
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
}
