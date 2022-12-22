package gab.DiscordBot.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class Command {
    protected String name;
    
    public boolean isThisCommand(String command) {
        return (name.compareTo(command) == 0);
    }

    public abstract void execute(SlashCommandInteractionEvent event);
}
