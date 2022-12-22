package gab.TwitchBot.Commands;

import java.io.IOException;

import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;

public class OptOutAttendanceCheckCommand extends Command {
    public OptOutAttendanceCheckCommand() {
        this.name = "optout";
    }

    @Override
    public void execute(CommandEvent event) throws IOException {
        String actor = event.getInnerEvent().getActor().getNick();

        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = ph.getProfile(actor);

        if(profile.isAttendanceOptedIn()) {
            profile.setAttendanceOptedIn(false);
            ph.setProfile(profile);
            ph.save();

            sendMessage(event, actor + " is no longer attending roll calls.");
        } else {
            sendMessage(event, actor + " was not attending roll calls.");
        }
    }
}