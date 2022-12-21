package gab.TwitchBot.Commands;

import java.io.IOException;

import gab.TwitchBot.TwitchBot;
import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;

public class OptInAttendanceCheckCommand extends Command{
    public OptInAttendanceCheckCommand(TwitchBot bot) {
        this.name = "optin";
    }

    @Override
    public void execute(CommandEvent event) throws IOException {
        String actor = event.getInnerEvent().getActor().getNick();
        
        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = ph.getProfile(actor);

        if(!profile.isAttendanceOptedIn()) {
            profile.setAttendanceOptedIn(true);
            ph.setProfile(profile);
            ph.save();

            sendMessage(event, actor + " is now attending the roll calls.");
        } else {
            sendMessage(event, actor + " is already attending the roll calls.");
        }
    }
}
