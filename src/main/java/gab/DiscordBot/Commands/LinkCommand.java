package gab.DiscordBot.Commands;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import gab.Utils.Profile;
import gab.Utils.ProfileHelper;
import gab.Utils.TwitchHelper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class LinkCommand extends Command {
    public LinkCommand() {
        this.name = "link";
        this.description = "Associate a Twitch account with this account.";
        this.options.add(new OptionData(OptionType.STRING, "twitch", "The name of the twitch account that you want to associate with this discord account.").setRequired(true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event)
            throws IOException, NameNotFoundException, InterruptedException {
        event.deferReply(true).setEphemeral(true).queue();
        
        OptionMapping twitchNameOption = event.getOption("twitch");

        String actor = event.getUser().getAsTag();

        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = null;
        
        try {
            profile = ph.getProfileByDiscord(actor);
        } catch(NameNotFoundException e){}

        if(twitchNameOption != null && !twitchNameOption.getAsString().isEmpty()) {
            TwitchHelper th = TwitchHelper.getInstance();
            String username = twitchNameOption.getAsString();
            String userId = th.getUserId(username);

            if(profile == null)
                profile = ph.getProfileByTwitchId(userId);

            if(profile.getTwitchName().compareTo(username) != 0)
                profile.setTwitchName(username);
            if(profile.getDiscordName().compareTo(actor) != 0)
                profile.setDiscordName(actor);

            ph.setProfile(profile);
            ph.save();

            String url = "https://www.twitch.tv/" + profile.getTwitchName();
            event.getHook().sendMessageEmbeds(new MessageEmbed(null, "Link twitch", "The twitch account [" + profile.getTwitchName() + "](" + url + ") is now associated with your discord.", null, null, 0x6600ff, null, null, null, null, null, null, null)).queue();
        }
        else
            event.getHook().sendMessage("A twitch name must be supplied to associate it with your discord.").queue();
    }
}
