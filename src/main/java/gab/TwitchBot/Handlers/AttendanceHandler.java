package gab.TwitchBot.Handlers;

import java.io.IOException;
import java.time.LocalDateTime;

import gab.TwitchBot.TwitchBot;
import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.Profile;
import gab.Utils.ProfileHelper;
import gab.Utils.TwitchHelper;

public class AttendanceHandler extends PostwareHandler {

    public AttendanceHandler(TwitchBot bot) {
        super(bot);
    }

    @Override
    public void execute(CommandEvent event) throws IOException, InterruptedException {
        String actor = event.getInnerEvent().getActor().getNick();

        ProfileHelper ph = ProfileHelper.getInstance();
        TwitchHelper th = TwitchHelper.getInstance();

        Profile profile = ph.getProfile(actor);
        LocalDateTime streamingSince = th.getStreamingSince();

        logger.log("Attendance check for " + actor);
        if(profile.isAttendanceOptedIn() && streamingSince != null && (profile.getLastAttendance() == null || profile.getLastAttendance().isBefore(streamingSince)))
        {
            logger.log("Attendace increased for " + actor);

            profile.setAttendanceCounter(profile.getAttendanceCounter() + 1);
            profile.setLastAttendance(LocalDateTime.now());

            ph.setProfile(profile);
            ph.save();
        }
    }
    
}
