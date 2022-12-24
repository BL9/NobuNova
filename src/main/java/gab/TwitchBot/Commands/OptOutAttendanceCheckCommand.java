package gab.TwitchBot.Commands;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;

public class OptOutAttendanceCheckCommand extends Command {
    public OptOutAttendanceCheckCommand() {
        this.name = "optout";
    }

    @Override
    public void execute(CommandEvent event) throws IOException, NameNotFoundException, InterruptedException {
        String actor = event.getInnerEvent().getActor().getNick();

        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = ph.getProfile(actor);

        if(profile.isAttendanceOptedIn()) {
            profile.setAttendanceOptedIn(false);
            ph.setProfile(profile);
            ph.save();

            sendMessage(event, actor + " has resigned from duty aboard the Mothership.");
        } else {
            sendMessage(event, actor + " has not signed up for duty.");
        }
    }
}