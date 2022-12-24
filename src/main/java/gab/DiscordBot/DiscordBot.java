package gab.DiscordBot;

import java.io.IOException;
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
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class DiscordBot extends ListenerAdapter {
    private final Logger logger;
    private final JDA jda;

    private final Set<Command> commands;

    private final CommandListUpdateAction listCommandsAction;
    public DiscordBot(ConfigHelper configHelper) throws IOException {
        this.logger = new Logger("Discord");
        this.commands = new HashSet<Command>();

        String token = configHelper.getValue("discord_token");
        String activity = configHelper.getValue("discord_activity");
        configHelper.save();

        jda = JDABuilder.createDefault(token)
            .addEventListeners(this)
            .build();

        listCommandsAction = jda.updateCommands().addCommands(
            Commands.slash("ping", "Calculate the ping of the bot.")
        );
    }

    public void addCommand(Command command) {
        commands.add(command);
        listCommandsAction.addCommands(Commands.slash(command.getName(), command.getDescription()));
    }

    public void updateCommands()
    {
        listCommandsAction.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        String user = event.getUser().getAsTag();

        logger.log("Command '" + commandName + "'received from: " + user);

        switch(event.getName())
        {
            case "ping":
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(true)
                    .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)
                    ).queue();
                return;
        }

        for (Command command : commands) {
            if(command.isThisCommand(commandName))
            {
                try {
                    command.execute(event);
                } catch (Exception e) {
                    logger.log("ERROR: " + e.getMessage());
                }
                return;
            }
        }
    }
}
