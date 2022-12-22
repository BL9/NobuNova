package gab.DiscordBot;

import java.util.HashSet;
import java.util.Set;

import gab.DiscordBot.Commands.Command;
import gab.Utils.ConfigHelper;
import gab.Utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class DiscordBot extends ListenerAdapter {
    private final Logger logger;
    private final JDA jda;

    private final Set<Command> commands;

    public DiscordBot(ConfigHelper configHelper) {
        this.logger = new Logger("Discord");
        this.commands = new HashSet<Command>();

        String token = configHelper.getValue("discord_token");
        String activity = configHelper.getValue("discord_activity");

        jda = JDABuilder.createDefault(token)
            .setActivity(Activity.listening(activity))
            .addEventListeners(this)
            .build();

        jda.updateCommands().addCommands(
            Commands.slash("ping", "Calculate the ping of the bot.")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        for (Command command : commands) {
            if(command.isThisCommand(event.getName()))
            {
                command.execute(event);
                return;
            }
        }

        switch(event.getName())
        {
            case "ping":
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(true)
                    .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)
                    ).queue();
                break;
        }
    }
}
