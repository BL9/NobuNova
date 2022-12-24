package gab.TwitchBot.Commands;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;

public class GetAttendanceCommand extends Command {
    public GetAttendanceCommand() {
        this.name = "attendance";
    }

    @Override
    public void execute(CommandEvent event) throws IOException, NameNotFoundException, InterruptedException {
        String actor = event.getInnerEvent().getActor().getNick();
        
        ProfileHelper ph = ProfileHelper.getInstance();
        Profile profile = ph.getProfile(actor);

        sendMessage(event, actor + " has sailed the stars with The Overlord and crew " + profile.getAttendanceCounter() + " times.");
    }
    
}
