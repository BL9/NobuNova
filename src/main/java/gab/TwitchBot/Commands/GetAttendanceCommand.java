package gab.TwitchBot.Commands;

import java.io.IOException;

import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;

public class GetAttendanceCommand extends Command {
    public GetAttendanceCommand() {
        this.name = "attendance";
    }

    @Override
    public void execute(CommandEvent event) throws IOException {
        String actor = event.getInnerEvent().getActor().getNick();
        
        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = ph.getProfile(actor);

        sendMessage(event, actor + ", you've attended roll call " + profile.getAttendanceCounter() + " times.");
    }
    
}
