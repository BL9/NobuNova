package gab.DiscordBot.Commands;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import gab.Utils.Profile;
import gab.Utils.ProfileHelper;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class GetAttendanceCommand extends Command {
    public GetAttendanceCommand() {
        this.name = "attendance";
        this.description = "Consult how often you have sailed the stars with The Overlord and the crew.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {
        String user = event.getUser().getAsTag();

        ProfileHelper ph = ProfileHelper.getInstance();
        
        try {
            Profile profile = ph.getProfileByDiscord(user);
            event.reply("You have sailed the stars with The Overlord and the crew " + profile.getAttendanceCounter() + " times.").setEphemeral(false).queue();

        } catch (NameNotFoundException e) {
            event.reply("You have not been found in our database.\nPlease register using the /link command first.").queue();
            e.printStackTrace();
        }
    }
    
}
