package gab.TwitchBot.Commands;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;

public class OptInAttendanceCheckCommand extends Command{
    public OptInAttendanceCheckCommand() {
        this.name = "optin";
    }

    @Override
    public void execute(CommandEvent event) throws IOException, NameNotFoundException, InterruptedException {
        String actor = event.getInnerEvent().getActor().getNick();
        
        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = ph.getProfile(actor);

        if(!profile.isAttendanceOptedIn()) {
            profile.setAttendanceOptedIn(true);
            ph.setProfile(profile);
            ph.save();

            sendMessage(event, actor + " has signed up for duty aboard the Mothership.");
        } else {
            sendMessage(event, actor + " is already signed up for duty.");
        }
    }
}
